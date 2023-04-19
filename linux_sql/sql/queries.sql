insert into cd.facilities(facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) values (9, 'Spa', 20, 30, 100000, 800);

insert into cd.facilities(facid, name, membercost, guestcost, initialoutlay, monthlymaintenance) values ((select max(facid) from cd.facilities) + 1,'Spa', 20, 30, 100000, 800);

update cd.facilities set initialoutlay = 10000 where name = 'Tennis Court 2';

update cd.facilities
set membercost = f2.membercost * 1.1,
guestcost = f2.guestcost * 1.1
from (select guestcost, membercost from cd.facilities where name = 'Tennis Court 1') as f2
where name = 'Tennis Court 2';

truncate cd.bookings;

delete from cd.members where memid = 37;

select facid, name, membercost, monthlymaintenance from cd.facilities
where membercost > 0 and membercost < monthlymaintenance / 50;

select * from cd.facilities where name like '%Tennis%';

select * from cd.facilities where facid in (1, 5);

Select memid, surname, firstname, joindate from cd.members
where joindate >= '2012-09-1';

select surname from cd.members 
union
select name from cd.facilities;

select starttime from cd.bookings
inner join cd.members on cd.bookings.memid = cd.members.memid
where firstname = 'David' and surname = 'Farrell';

select cd.bookings.starttime as start, cd.facilities.name as name 
from cd.bookings
inner join cd.facilities on cd.bookings.facid = cd.facilities.facid
where starttime >= '2012-09-21' and starttime < '2012-9-22' and cd.facilities.name like '%Tennis Court%'
order by cd.bookings.starttime;

select m1.firstname as memfname, m1.surname as memsname,
m2.firstname as recfname, m2.surname as recsname
from cd.members as m1
left join cd.members as m2 on m1.recommendedby = m2.memid
order by m1.surname, m1.firstname;

select m1.firstname as memfname, m1.surname as memsname,
m2.firstname as recfname, m2.surname as recsname
from cd.members as m1
left join cd.members as m2 on m1.recommendedby = m2.memid
order by m1.surname, m1.firstname;

select distinct m1.firstname, m1.surname
from cd.members as m1
inner join cd.members as m2 on m1.memid = m2.recommendedby
order by surname, firstname;

select distinct firstname || ' ' || surname as member, 
  (select firstname || ' ' || surname as recommender 
   from cd.members 
   where memid = m1.recommendedby)
from cd.members as m1
order by member;

select recommendedby, count(*) as count
from cd.members
group by recommendedby
having recommendedby is not null
order by recommendedby;

select facid, sum(slots) as "Total Slots"
from cd.bookings
group by facid
order by facid;

select facid, sum(slots) as "Total Slots"
from cd.bookings
where starttime >= '2012-09-01' and starttime < '2012-10-01' 
group by facid
order by sum(slots);

select facid, extract(month from starttime) as month, sum(slots) as "Total Slots"
from cd.bookings
where extract(year from starttime) = 2012
group by facid, month
order by facid, month;

select count(distinct memid) from cd.bookings;

select surname, firstname, cd.members.memid, min(starttime)
from cd.members
left join cd.bookings on cd.members.memid = cd.bookings.memid
where starttime >= '2012-09-01'
group by cd.members.memid
order by cd.members.memid;

select count(*) over(), firstname, surname
from cd.members;

select row_number() over(), firstname, surname
from cd.members
order by joindate;

select facid, total from (
  select f.facid, sum(b.slots) as total, rank() over (order by    
  sum(b.slots) desc) as rank
  from cd.facilities as f
  inner join cd.bookings as b on f.facid = b.facid
  group by f.facid) as ranked 
where rank = 1;

select surname || ', ' || firstname from cd.members;

select memid, telephone from cd.members
where telephone like '(%)%'
order by memid;

Select substr(surname, 1, 1) as letter, count(*) as count 
from cd.members
group by letter
order by letter;