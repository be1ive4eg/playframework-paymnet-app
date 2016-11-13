create table tbl_account (
  id                            bigint not null,
  balance                       decimal(19,4),
  ccy                           varchar(3),
  creation_date                 timestamp not null,
  last_update                   timestamp not null,
  version                       bigint not null,
  constraint ck_tbl_account_ccy check ( ccy in ('USD','RUB')),
  constraint pk_tbl_account primary key (id)
);
create sequence TBL_ACCOUNT_seq;

create table tbl_trx (
  id                            bigint not null,
  from_acc                      bigint,
  from_ccy                      varchar(3),
  from_amount                   decimal(19,4),
  to_acc                        bigint,
  to_ccy                        varchar(3),
  to_amount                     decimal(19,4),
  currency_rate                 double,
  type                          varchar(8),
  status                        varchar(6),
  creation_date                 timestamp not null,
  last_update                   timestamp not null,
  version                       bigint not null,
  constraint ck_tbl_trx_from_ccy check ( from_ccy in ('USD','RUB')),
  constraint ck_tbl_trx_to_ccy check ( to_ccy in ('USD','RUB')),
  constraint ck_tbl_trx_type check ( type in ('TRANSFER')),
  constraint ck_tbl_trx_status check ( status in ('DRAFT','POSTED')),
  constraint pk_tbl_trx primary key (id)
);
create sequence TBL_TRX_seq;

create table tbl_trx_entry (
  id                            bigint not null,
  trx_id                        bigint,
  acc                           bigint,
  ccy                           varchar(3),
  debit                         decimal(19,4),
  credit                        decimal(19,4),
  creation_date                 timestamp not null,
  last_update                   timestamp not null,
  version                       bigint not null,
  constraint ck_tbl_trx_entry_ccy check ( ccy in ('USD','RUB')),
  constraint pk_tbl_trx_entry primary key (id)
);
create sequence TBL_TRX_ENTRY_seq;

