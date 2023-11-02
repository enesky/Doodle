#!/bin/sh
# same file is added to project/.git/hooks/

echo "*************************************************"
echo "*                Running Detekt                 *"
echo "*************************************************"

OUTPUT="/tmp/detekt-$(date +%s)"
./gradlew detektDevPremiumDebug > $OUTPUT
EXIT_CODE=$?
if [ $EXIT_CODE -ne 0 ]; then
  cat $OUTPUT
  rm $OUTPUT
  echo "*************************************************"
  echo "*                 Detekt failed                 *"
  echo "*  Please fix the above issues before pushing   *"
  echo "*************************************************"
  exit $EXIT_CODE
else
  echo "*************************************************"
  echo "*               Detekt succeeded                *"
  echo "*************************************************"
fi
rm $OUTPUT