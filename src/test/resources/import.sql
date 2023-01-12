
insert into Passenger (birthday, firstName, lastName, registrationDate, id)
            values ('1999-11-23', 'Max', 'Customer1', '2021-02-17', 101);
insert into Passenger (birthday, firstName, lastName, registrationDate, id)
            values ('1999-11-24', 'Max', 'Customer2', '2021-02-18', 102);
insert into Passenger (birthday, firstName, lastName, registrationDate, id)
            values ('1999-11-25', 'Max', 'Customer3', '2021-02-19', 103);
insert into Passenger (birthday, firstName, lastName, registrationDate, id)
            values ('1999-11-26', 'Max', 'Customer4', '2021-02-20', 104);
insert into Passenger (birthday, firstName, lastName, registrationDate, id)
            values ('1999-11-27', 'Max', 'Customer5', '2021-02-21', 105);

insert into Unicorn (id, name, maxSpeedKmpH, weightKg)
            values (101, 'Magic Bubble Gum', 123, 63);
insert into Unicorn (id, name, maxSpeedKmpH, weightKg)
            values (102, 'Mystic Rainbow Tail', 111, 93);
insert into Unicorn (id, name, maxSpeedKmpH, weightKg)
            values (103, 'Starlight Twinkles', 143, 73);
insert into Unicorn (id, name, maxSpeedKmpH, weightKg)
            values (104, 'Glitter Star Magic', 162, 58);

insert into Reservation (id, reservation_date, unicorn_id, passenger_id)
            values(101, '2023-01-22', 102, 101);
insert into Reservation (id, reservation_date, unicorn_id, passenger_id)
            values(102, '2023-01-22', 102, 102);
insert into Reservation (id, reservation_date, unicorn_id, passenger_id)
            values(103, '2023-01-23', 103, 101);
insert into Reservation (id, reservation_date, unicorn_id, passenger_id)
            values(104, '2023-01-23', 103, 102);
insert into Reservation (id, reservation_date, unicorn_id, passenger_id)
            values(105, '2023-01-24', 104, 104);
