#!/bin/sh

wget -nc http://www2.census.gov/topics/genealogy/1990surnames/dist.all.last
wget -nc http://www2.census.gov/topics/genealogy/1990surnames/dist.female.first
wget -nc http://www2.census.gov/topics/genealogy/1990surnames/dist.male.first

importNames () {
    head -n 100 "$1" | cut -f 1 -d ' ' | while read firstName
    do
        head -n 100 dist.all.last | cut -f 1 -d ' ' | while read lastName
        do
            curl -s -H "Content-Type: application/json" -X POST -d "{\"fullName\":\"$firstName $lastName\",\"jobTitle\":\"Job Title\"}" "http://localhost:7080/people"
        done
    done
}

importNames 'dist.female.first'
importNames 'dist.male.first'

