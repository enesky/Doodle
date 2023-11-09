#!/bin/sh
# same file is added to project/.git/hooks/

#echo "*************************************************"
#echo "*         Running Detekt Auto Correct           *"
#echo "*************************************************"
#
#OUTPUT="/tmp/detekt-$(date +%s)"
#./gradlew detektAll --auto-correct > $OUTPUT
#EXIT_CODE=$?
#if [ $EXIT_CODE -ne 0 ]; then
#  cat $OUTPUT
#  rm $OUTPUT
#  echo "*************************************************"
#  echo "*         Detekt Auto Correct failed            *"
#  echo "*************************************************"
#  exit $EXIT_CODE
#else
#  echo "*************************************************"
#  echo "*         Detekt Auto Correct failed            *"
#  echo "*************************************************"
#fi
#rm $OUTPUT