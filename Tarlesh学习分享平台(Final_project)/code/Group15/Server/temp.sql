set global time_zone = '+8:00';
use filesystem;

drop procedure commentOnFile;

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