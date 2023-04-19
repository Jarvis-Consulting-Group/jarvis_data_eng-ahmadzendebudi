# Introduction
A number of SQL practice problems along with my proposed solution. All the solutions were tested individually and all produce the desired output. These queries can also be found in [queries.sql](./queries.sql).
## Modification to Data

##### Problem:
Adding a new row to `cd.facilities` with values: `facid: 9, Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800`
##### Solution:
```sql
insert into cd.facilities(facid, name, membercost,
guestcost, initialoutlay, monthlymaintenance) 
values (9, 'Spa', 20, 30, 100000, 800);
```

---

##### Problem:
Adding a new row to `cd.facilities` with values: `Name: 'Spa', membercost: 20, guestcost: 30, initialoutlay: 100000, monthlymaintenance: 800`, `facid` auto generated 

##### Solution:
```sql
insert into cd.facilities(facid, name, membercost,
 guestcost, initialoutlay, monthlymaintenance) 
 values ((select max(facid) from cd.facilities) + 1,
 'Spa', 20, 30, 100000, 800)
```

---

##### Problem:
Update a row with name `Tennis Court 2` to have `initialoutlay = 10000` 

##### Solution:
```sql
update cd.facilities set initialoutlay = 10000 where name = 'Tennis Court 2'
```

---
##### Problem:
Update `Tennis Court 2` to have cost 10% higher than `Tennis Court 1`
##### Solution:
```sql
update cd.facilities
set membercost = f2.membercost * 1.1,
guestcost = f2.guestcost * 1.1
from (select guestcost, membercost from cd.facilities where name = 'Tennis Court 1') as f2
where name = 'Tennis Court 2'
```
---
##### Problem:
Remove all entries in the `cd.booking` table
##### Solution:
```sql
truncate cd.bookings
```
---
##### Problem:
Remove entry with id 37 in the `cd.booking` table
##### Solution:
```sql
delete from cd.members where memid = 37
```
---

## Basics
##### Problem:
View facilities that charge a fee and the fee is less than 1/50th of the monthly maintenance
##### Solution:
```sql
select facid, name, membercost, monthlymaintenance from cd.facilities
where membercost > 0 and membercost < monthlymaintenance / 50
```
---

##### Problem:
All facilities with word `Tennis` in their name
##### Solution:
```sql
select * from cd.facilities where name like '%Tennis%';
```
---

##### Problem:
Facilities with id 1 or 5:
##### Solution:
```sql
select * from cd.facilities where facid in (1, 5);
```
---
##### Problem:
Members who joined after the start of September 2012
##### Solution:
```sql
Select memid, surname, firstname, joindate from cd.members
where joindate >= '2012-09-1';
```
---
##### Problem:
A list of surnames of members and facility names
##### Solution:
```sql
select surname from cd.members 
union
select name from cd.facilities;
```
---

## Join
##### Problem:
A list of starttimes by members named 'David Farrell'
##### Solution:
```sql
select starttime from cd.bookings
inner join cd.members on cd.bookings.memid = cd.members.memid
where firstname = 'David' and surname = 'Farrell'
```

---
##### Problem:
A list of starttimes and facility name for only Tennis courts on `2012-09-21` 
##### Solution:
```sql
select cd.bookings.starttime as start, cd.facilities.name as name 
from cd.bookings
inner join cd.facilities on cd.bookings.facid = cd.facilities.facid
where starttime >= '2012-09-21' and starttime < '2012-9-22' and cd.facilities.name like '%Tennis Court%'
order by cd.bookings.starttime 
```
---
##### Problem:
Select all members and also show who recommended them, if any
##### Solution:
```sql
select m1.firstname as memfname, m1.surname as memsname,
m2.firstname as recfname, m2.surname as recsname
from cd.members as m1
left join cd.members as m2 on m1.recommendedby = m2.memid
order by m1.surname, m1.firstname
```
---
##### Problem:
Select all members and also show who recommended them, if any
##### Solution:
```sql
select m1.firstname as memfname, m1.surname as memsname,
m2.firstname as recfname, m2.surname as recsname
from cd.members as m1
left join cd.members as m2 on m1.recommendedby = m2.memid
order by m1.surname, m1.firstname
```
---
##### Problem:
Select all members who recommended another member
##### Solution:
```sql
select distinct m1.firstname, m1.surname
from cd.members as m1
inner join cd.members as m2 on m1.memid = m2.recommendedby
order by surname, firstname
```
---
##### Problem:
A list of all members and their recommender without using join
##### Solution:
```sql
select distinct firstname || ' ' || surname as member, 
  (select firstname || ' ' || surname as recommender 
   from cd.members 
   where memid = m1.recommendedby)
from cd.members as m1
order by member
```

---
##### Problem:
A list of all members and the number of recommendations they made
##### Solution:
```sql
select recommendedby, count(*) as count
from cd.members
group by recommendedby
having recommendedby is not null
order by recommendedby
```

---
##### Problem:
A list of total number of slots booked in each facility
##### Solution:
```sql
select facid, sum(slots) as "Total Slots"
from cd.bookings
group by facid
order by facid
```

---
##### Problem:
A list of total number of slots booked in each facility in the month of September 2012
##### Solution:
```sql
select facid, sum(slots) as "Total Slots"
from cd.bookings
where starttime >= '2012-09-01' and starttime < '2012-10-01' 
group by facid
order by sum(slots)
```
---
##### Problem:
A list of total number of slots booked in each facility and each month in the year of 2012
##### Solution:
```sql
select facid, extract(month from starttime) as month, sum(slots) as "Total Slots"
from cd.bookings
where extract(year from starttime) = 2012
group by facid, month
order by facid, month
```
---
##### Problem:
Total number of members who made at least one booking
##### Solution:
```sql
select count(distinct memid) from cd.bookings
```
---
##### Problem:
A list of each member and their first booking after `2012-09-01`
##### Solution:
```sql
select surname, firstname, cd.members.memid, min(starttime)
from cd.members
left join cd.bookings on cd.members.memid = cd.bookings.memid
where starttime >= '2012-09-01'
group by cd.members.memid
order by cd.members.memid
```
---
##### Problem:
A list of each member and each with the total number of members count as one column
##### Solution:
```sql
select count(*) over(), firstname, surname
from cd.members
```
---
##### Problem:
A list of each member ordered by date of joining. The table should include a row number column too.
##### Solution:
```sql
select row_number() over(), firstname, surname
from cd.members
order by joindate
```
---
##### Problem:
Output the facility id that has the most number of slots booked. output multiple ones if more than one have the maximum number.
##### Solution:
```sql
select facid, total from (
  select f.facid, sum(b.slots) as total, rank() over (order by    
  sum(b.slots) desc) as rank
  from cd.facilities as f
  inner join cd.bookings as b on f.facid = b.facid
  group by f.facid) as ranked 
where rank = 1
```
---
##### Problem:
Output member names formated as `surname, firstname`
##### Solution:
```sql
select surname || ', ' || firstname from cd.members
```

---
##### Problem:
Output member id and the telephone number only if the telephone number has parentheses
##### Solution:
```sql
select memid, telephone from cd.members
where telephone like '(%)%'
order by memid
```
---
##### Problem:
Output a list of the count of members who's surname starts with a specific alphabetic character
##### Solution:
```sql
Select substr(surname, 1, 1) as letter, count(*) as count 
from cd.members
group by letter
order by letter
```