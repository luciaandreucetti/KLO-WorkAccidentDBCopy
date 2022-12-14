# -- !Ups

create table work_accidents(
    id serial PRIMARY KEY,
    date_time timestamp,
    entrepreneur_id int,
    location text,
    region_id int,
    blog_post_url varchar(1024),
    details text,
    investigation text,
    initial_source varchar(1024),
    media_reports text,
    public_remarks text,
    sensitive_remarks text,

    constraint fk_wa_ent foreign key(entrepreneur_id) references business_entities(id) on delete set null,
    constraint fk_wa_rgn foreign key(region_id) references regions(id) on delete set null
);

create table injured_workers(
    id serial PRIMARY KEY,
    accident_id int,
    name varchar(512),
    age int,
    citizenship_id int,
    industry_id int,
    employer_id int,
    from_place varchar(512),
    injury_cause_id int,
    injury_severity int,
    injury_description text,
    public_remarks text,
    sensitive_remarks text,

    constraint fk_iw_wa foreign key (accident_id) references work_accidents(id) on delete cascade,
    constraint fk_iw_be foreign key (employer_id) references business_entities(id) on delete set null,
    constraint fk_iw_cz foreign key (citizenship_id) references citizenships(id) on delete set null,
    constraint fk_iw_in foreign key (industry_id) references industries(id) on delete set null,
    constraint fk_iw_ic foreign key (injury_cause_id) references injury_causes(id) on delete set null
);

create index iw_by_ac on injured_workers(accident_id);
create index iw_by_be on injured_workers(employer_id);
create index iw_by_sv on injured_workers(injury_severity);
create index iw_by_cs on injured_workers(injury_cause_id);

# -- !Downs
drop index iw_by_ac;
drop index iw_by_be;
drop index iw_by_sv;
drop index iw_by_cs;
drop table injured_workers;
drop table work_accidents;