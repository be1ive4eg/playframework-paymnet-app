package com.hiddensign.core.common;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.EbeanServerFactory;
import com.avaje.ebean.config.DocStoreConfig;
import com.avaje.ebean.config.PropertiesWrapper;
import com.avaje.ebean.config.ServerConfig;
import com.avaje.ebean.config.dbplatform.DbIdentity;
import com.avaje.ebean.config.dbplatform.H2Platform;
import com.avaje.ebean.config.dbplatform.IdType;
import com.avaje.ebean.config.dbplatform.PostgresPlatform;
import com.google.inject.AbstractModule;
import com.hiddensign.core.common.data.BaseModel;
import com.hiddensign.core.common.utils.PropertyUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.avaje.agentloader.AgentLoader;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Nikolay Denisenko
 * @version 2016/11/12
 */
public class GuiceEbeanConfigModule extends AbstractModule {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuiceEbeanConfigModule.class);

    private static final String DB_PROP = "db";

    private static final String DB_ENHANCE_PROP = "enhance";
    private static final String DB_ENHANCE_PACKAGES_PROP = "packages";
    private static final String DB_ENHANCE_DEBUG_PROP = "debug";

    private static final String DB_NAMES_PROP = "names";
    private static final String DB_NAMES_DEFAULT = "h2";

    private static final String DB_POOL_PROP = "pool";
    private static final String DB_POOL_DEFAULT= "hikaricp";

    private static final String DB_DEFAULT_PROP = "default";

    private static final String DB_MODEL_PROP = "models";

    @Override
    protected void configure() {
        Properties props = PropertyUtils.loadPropertiesFromFile();

        //enchance all packages
        String dbEnhancePackages = props.
                getProperty(DB_PROP + "." + DB_ENHANCE_PROP + "." + DB_ENHANCE_PACKAGES_PROP);
        String dbEnhanceDebug = props.
                getProperty(DB_PROP + "." + DB_ENHANCE_PROP + "." + DB_ENHANCE_DEBUG_PROP);

        if (!AgentLoader.loadAgentFromClasspath("ebean-agent",
                "debug=" + dbEnhanceDebug + ";packages=" + dbEnhancePackages))
        {
            LOGGER.info("Load avaje-ebeanorm-agent not found in classpath - not dynamically loaded");
        } else {
            LOGGER.info("Load agent from classpath success");
        }

        //load all databases
        String[] dbNames = props.
                getProperty(DB_PROP + "." + DB_NAMES_PROP, DB_NAMES_DEFAULT).split(",");

        for (String dbName : dbNames) {
            Properties dbProps =
                    PropertyUtils.extractPrefixedProperties(props, DB_PROP + "." + dbName + ".", false);

            String dbPool = dbProps.
                    getProperty(DB_POOL_PROP, DB_POOL_DEFAULT);
            String isDefault = dbProps.
                    getProperty(DB_DEFAULT_PROP, "false");
            String[] models = dbProps.
                    getProperty(DB_MODEL_PROP, "").split(",");

            //bind ebean
            bind(EbeanServer.class).toProvider(() -> {

                ServerConfig serverConfig;
                switch (dbPool) {
                    case "hikaricp":
                        String prefix = dbPool + ".";
                        HikariConfig hikariConfig = new HikariConfig();
                        TargetProperties.setTargetFromProperties(hikariConfig, dbProps, prefix);
                        DataSource dataSource = new HikariDataSource(hikariConfig);

                        H2Platform h2Platform = new H2Platform();
                        DbIdentity dbIdentity = h2Platform.getDbIdentity();
                        dbIdentity.setSupportsGetGeneratedKeys(false);
                        dbIdentity.setSupportsSequence(true);
                        dbIdentity.setIdType(IdType.SEQUENCE);

                        serverConfig = new ServerConfig();
                        serverConfig.setName(dbName);
                        serverConfig.setDatabasePlatform(h2Platform);

                        serverConfig.setDataSource(dataSource);

                        break;
                    default:
                        throw new RuntimeException("Illegal pool name");
                }
                serverConfig.setDatabaseBooleanFalse("N");
                serverConfig.setDatabaseBooleanTrue("Y");
                serverConfig.setDefaultServer(BooleanUtils.toBoolean(isDefault));
                for (String model : models) {
                    if (model.endsWith(".*")) {
                        Reflections reflections = new Reflections(model.substring(0, model.length() - 2));
                        reflections.getSubTypesOf(BaseModel.class).forEach(serverConfig::addClass);
                    } else {
                        serverConfig.addClass(ReflectionUtils.forName(model));
                    }
                }

                serverConfig.setDdlGenerate(true);
                serverConfig.setDdlRun(true);

                return EbeanServerFactory.create(serverConfig);
            }).asEagerSingleton();
        }
    }


}
