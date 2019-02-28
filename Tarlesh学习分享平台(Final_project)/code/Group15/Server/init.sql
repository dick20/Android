create database filesystem;
set global time_zone = '+8:00';
use filesystem;

create table if not exists users(
	uid char(20) primary key,
    uname char(16) not null unique,
    pwd char(20) not null,
    email char(20) not null,
    phone char(12) not null,
    points integer default 0
)ENGINE = InnoDB;


create table if not exists files(
	fid char(50) primary key,
    fname char(50) not null,
    uid char(20),
    description char(80) not null,
    createTime datetime not null,
    foreign key(uid) references users(uid) on delete no action
)ENGINE = InnoDB;

create table if not exists comments(
	cid char(20) primary key,
    uid char(20),
    fid char(50),
    points integer default 0,
    content text not null,
    createTime datetime not null,
    foreign key(uid) references users(uid) on delete no action,
    foreign key(fid) references files(fid) on delete cascade
)ENGINE = InnoDB;


create table if not exists operatRecord(
	oid char(50) primary key,
    uid char(20),
    fid char(50),
    operate char(20) not null,
    createTime datetime not null,
	foreign key(uid) references users(uid) on delete no action,
    foreign key(fid) references files(fid) on delete no action
)ENGINE = InnoDB;


create table if not exists questions(
	qid char(20) primary key,
    uid char(20),
    title char(30) not null,
    content text not null,
    createTime datetime not null,
    foreign key(uid) references users(uid) on delete no action
)ENGINE =InnoDB;

create table if not exists answers(
	aid char(20) primary key,
    uid char(20),
    qid char(20),
    content text,
    createTime datetime not null,
    foreign key(uid) references users(uid) on delete no action,
    foreign key(qid) references questions(qid) on delete cascade
)ENGINE = InnoDB;


## 1 为上传，-1为下载

delimiter //
create procedure upload(argfid char(50), argfname char(50),arguid char(20),argdescription char(80))
deterministic
begin
# declare total_price integer;
	# 暂时不搞积分

	# 添加文件记录
	insert into files(fid,uid,fname,description,createTime) values(argfid, arguid,argfname,argdescription,NOW());

	# 添加操作记录,直接用同一个id算了ε=ε=ε=(~￣▽￣)~
	insert into operatRecord(oid,uid,fid,operate,createTime) values(argfid,arguid,argfid,'upload',NOW());
    
	select fid,points,fname,u.uid,uname,description,createTime from (select f.fid ,points,fname,uid,description,createTime from 
						(select * from files where fid = argfid) as f  
										left join 
										(select fid, avg(points) as points from comments group by fid) as c
										on c.fid = f.fid) as fy
                        left join (select uid,uname from users)as u on u.uid = fy.uid;
    select cid,uname,content,points,createTime from (select * from comments where fid = argfid) as c 
			left join (select uid,uname from users) as u on u.uid = c.uid;
	
	update users set points = points +10 where uid = arguid;
    commit;
end //
delimiter ;


delimiter //
create procedure download(argoid char(50), argfid char(50), arguid char(20))
deterministic
begin
	# 暂时不搞积分

	# 添加操作记录
	insert into operatRecord(oid,uid,fid,operate,createTime) values(argoid,arguid,argfid,'download',NOW());
    
    
	select fid,points,fname,u.uid,uname,description,createTime from (select f.fid ,points,fname,uid,description,createTime from 
						(select * from files where fid = argfid) as f  
										left join 
										(select fid, avg(points) as points from comments group by fid) as c
										on c.fid = f.fid) as fy
                        left join (select uid,uname from users)as u on u.uid = fy.uid;
    select cid,uname,content,points,createTime from (select * from comments where fid = argfid) as c 
			left join (select uid,uname from users) as u on u.uid = c.uid;
    commit;
end //
delimiter ;

delimiter //
create procedure getFileList(queryfilename char(20))
deterministic
begin
	# 暂时不搞积分

	# 添加操作记录
	select fz.fid,uname,fname,points,createTime,description,download_count 
		from ( select fy.fid,u.uname,fy.fname,fy.points,fy.createTime,fy.description from 
			(select f.fid as fid,fname,uid,description,createTime,points from (select * from files where fname like queryfilename) as f 
				left join 
					(select fid, avg(points)as points from comments group by fid) as c on c.fid = f.fid) 
			as fy left join users u on u.uid = fy.uid) as fz 
					left join 
						(select fid,count(*) as download_count from operatRecord 
						where operate = 'download' group by fid) as op 
						on op.fid = fz.fid order by createTime DESC;
                     
    commit;
end //
delimiter ;


delimiter //
create procedure getFileDetail(argfid char(20))
deterministic
begin
	# 暂时不搞积分

	# 添加操作记录
	select fid,points,fname,u.uid,uname,description,createTime from (select f.fid ,points,fname,uid,description,createTime from 
						(select * from files where fid = argfid) as f  
										left join 
										(select fid, avg(points) as points from comments group by fid) as c
										on c.fid = f.fid) as fy
                        left join (select uid,uname from users)as u on u.uid = fy.uid;
    select cid,uname,content,points,createTime from (select * from comments where fid = argfid) as c 
			left join (select uid,uname from users) as u on u.uid = c.uid;
            
    commit;
end //
delimiter ;
	

delimiter //
create procedure commentOnFile(argcid char(20), argfid char(50), arguid char(20),argcontent text,argpoints integer)
deterministic
begin
	# 暂时不搞积分
	
	select count(fid) into @c from comments where fid = argfid and uid = arguid;
	
	if @c != 0 then
		select * from files where 1 = 2;
	else
		# 添加操作记录
		insert into comments(cid,fid,uid,content,points,createTime) values(argcid,argfid,arguid,argcontent,argpoints,NOW());
		
		update users set points = points +1 where uid = arguid;
		
		select cid,uname,content,points,createTime from (select * from comments where fid = argfid) as c 
			left join (select uid,uname from users) as u on u.uid = c.uid;
	end if;
    
    commit;
end //
delimiter ;


delimiter //
create procedure getQuestionList()
deterministic
begin
	# 暂时不搞积分
    select qid,u.uname,title,content,createTime,answers_counts from 
		(select q.qid,uid,title,content,createTime,answers_counts from 
			questions q left join 
				(select qid, count(*) as answers_counts from answers group by qid) as a 
						on q.qid = a.qid) as qa left join (select uid,uname from users) as u 
							on u.uid = qa.uid order by createTime DESC;
    commit;
end //
delimiter ;


delimiter //
create procedure createQuestion(argqid char(20),arguid char(20),argtitle char(30),argcontent text)
deterministic
begin
	# 暂时不搞积分
    
    insert into questions(qid,uid,title,content,createTime) values(argqid,arguid,argtitle,argcontent,NOW());
	
	update users set points = points +2 where uid = arguid;
    select qid,uname,title,content,createTime from (select * from questions where qid = argqid) as q
		left join (select uid,uname from users) as u on u.uid = q.uid;
    commit;
end //

delimiter ;


delimiter //
create procedure getQuestion(argqid char(20))
deterministic
begin
	# 暂时不搞积分
    
	select qid,uname,title,content,createTime from (select * from questions where qid = argqid) as q
		left join (select uid,uname from users) as u on u.uid = q.uid;
        
    select aid,content,uname,createTime from 
		(select * from answers where qid = argqid) as a
			left join (select uid,uname from users) as u
				on a.uid = u.uid;
    commit;
end //
delimiter ;

delimiter //
create procedure answerQuestion(argaid char(20),argqid char(20),arguid char(20),argcontent text)
deterministic
begin
	
    insert into answers(aid,uid,qid,content,createTime)values(argaid,arguid,argqid,argcontent,NOW());
    
	update users set points = points +3 where uid = arguid;

	select aid,content,uname,createTime from 
		(select * from answers where qid = argqid) as a
			left join (select uid,uname from users) as u
				on a.uid = u.uid;
	
    commit;
end //
delimiter ;


delimiter //
create procedure fileRank(queryfilename char(20))
deterministic
begin
	select fz.fid,uname,fname,points,createTime,description,download_count from ( select fy.fid,u.uname,fy.fname,fy.points,fy.createTime,fy.description from 
		(select f.fid as fid,fname,uid,description,createTime,points from (select * from files where fname like queryfilename) as f 
			left join 
				(select fid, avg(points)as points from comments group by fid) as c on c.fid = f.fid) 
		as fy left join users u on u.uid = fy.uid) as fz 
				left join 
					(select fid,count(*) as download_count from operatRecord where operate = 'download' group by fid) as op 
					on op.fid = fz.fid order by download_count DESC;
end //
delimiter ;

delimiter //
create procedure fileRank2(queryfilename char(20))
deterministic
begin
	select fz.fid,uname,fname,points,createTime,description,download_count from ( select fy.fid,u.uname,fy.fname,fy.points,fy.createTime,fy.description from 
		(select f.fid as fid,fname,uid,description,createTime,points from (select * from files where fname like queryfilename) as f 
			left join 
				(select fid, avg(points)as points from comments group by fid) as c on c.fid = f.fid) 
		as fy left join users u on u.uid = fy.uid) as fz 
				left join 
					(select fid,count(*) as download_count from operatRecord where operate = 'download' group by fid) as op 
					on op.fid = fz.fid order by points DESC;
end //
delimiter ;
delimiter //
create procedure userRank()
deterministic
begin
	select uname, counts from users u  left join (select uid, count(*) as counts from operatRecord  where operate = 'upload'
						group by uid) as op on op.uid = u.uid 
						order by counts DESC;
    commit;
end //
delimiter ;
















