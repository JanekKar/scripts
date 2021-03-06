#! /bin/bash

# Simple script for calculating amount of time spend on task

# Parses uptime to format 'hh mm'
function uptime_raw(){
  UPTIME=`uptime | awk '{print $3}' | sed 's/,//g' | sed 's/:/ /g'`
  echo "$UPTIME"
}

# Formats time
function format_time(){
    HOURS=$(($1 / 60))
    MINUTES=$(($1 % 60))
    if [ $HOURS -eq 0 ]
    then
      echo "$MINUTES""m"
    else
      echo "$HOURS""h""$MINUTES""m"
    fi
}

# Formats uptime to format '1h23m'
function format_uptime(){
  uptime=`uptime_in_mins`
  echo "`format_time $uptime`"
}

# Converts current uptime to minutes
function uptime_in_mins(){
  time=`uptime_raw`
  echo "`time_to_mins $time`"
}

# Converts time to minutes args:
function time_to_mins(){
    if [ $# -eq 1 ]
    then
      echo "$1"
    else
      echo $(($1 * 60 + 10#$2))
    fi
}

if [ $# -gt 0 ]
then
  matching=`echo "$1" | sed -n '/^\([[:digit:]]\?[[:digit:]]h\)\?\([[:digit:]]\?[[:digit:]]m\)\?$/p'`

  if [ ${#matching} -gt 0 ]
  then
    TIME=`echo "$1" | sed 's/h/ 0/g' | sed 's/m//g'`
    TIME_IN_MINS=`time_to_mins $TIME`
    UPTIME_IN_MINS=`uptime_in_mins`

    DIFF_MIN=$((UPTIME_IN_MINS - TIME_IN_MINS))
    format_time $DIFF_MIN
  else
    if [ $1 == "--help" ]
    then
      echo "Usage:"
      echo "  calctime - prints foramted uptime"
      echo "  calctime [formated_time] - prints differenc between uptime and passed time"
      echo "    eg: calctime 1h23m"
      echo
      echo "Time Formating"
      echo "yyhxxm - yy hours and xx minutes"
    else
      echo "Wrong arg '$1' see '--help' for instructions."
    fi
  fi

else
  format_uptime
fi
