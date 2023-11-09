#!/bin/sh
# same file is added to project/.git/hooks/

# -------------- Functions --------------
function run_detekt_auto_correct() {
  echo "*************************************************"
  echo "*         Running Detekt Auto Correct           *"
  echo "*************************************************"

  OUTPUT="/tmp/detekt-$(date +%s)"
  ./gradlew detektAll --auto-correct > $OUTPUT
  EXIT_CODE=$?

  if [ $EXIT_CODE -ne 0 ]; then
    cat $OUTPUT
    rm $OUTPUT
    echo "*************************************************"
    echo "*         Detekt Auto Correct failed            *"
    echo "*************************************************"
    exit $EXIT_CODE
  else
    echo "*************************************************"
    echo "*       Detekt Auto Correct succeeded           *"
    echo "*************************************************"
  fi
  rm $OUTPUT
}

function run_detekt() {
  echo "-------------------------------------------------"
  echo "*************************************************"
  echo "*                Running Detekt                 *"
  echo "*************************************************"

  OUTPUT="/tmp/detekt-$(date +%s)"
  ./gradlew detektAll > $OUTPUT
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
}

function run_spotless_apply() {
  echo "*************************************************"
  echo "*            Running Spotless Apply             *"
  echo "*************************************************"

  OUTPUT="/tmp/detekt-$(date +%s)"
  ./gradlew spotlessApply > $OUTPUT
  EXIT_CODE=$?

  if [ $EXIT_CODE -ne 0 ]; then
    cat $OUTPUT
    rm $OUTPUT
    echo "*************************************************"
    echo "*            Spotless Apply failed              *"
    echo "*  Please fix the above issues before pushing   *"
    echo "*************************************************"
    exit $EXIT_CODE
  else
    echo "*************************************************"
    echo "*          Spotless Apply succeeded             *"
    echo "*************************************************"
  fi
  rm $OUTPUT
}

function run_spotless_check() {
  echo "*************************************************"
  echo "*            Running Spotless Check             *"
  echo "*************************************************"

  OUTPUT="/tmp/detekt-$(date +%s)"
  ./gradlew spotlessCheck > $OUTPUT
  EXIT_CODE=$?

  if [ $EXIT_CODE -ne 0 ]; then
    cat $OUTPUT
    rm $OUTPUT
    echo "*************************************************"
    echo "*            Spotless Check failed              *"
    echo "*  Please fix the above issues before pushing   *"
    echo "*************************************************"
    exit $EXIT_CODE
    run_spotless_apply
  else
    echo "*************************************************"
    echo "*          Spotless Check succeeded             *"
    echo "*************************************************"
  fi
  rm $OUTPUT
}
# --------------  End of Functions --------------


# --------------  Running Functions -------------
echo "-----------------------------------------------------"
echo "        Spotless & Detekt scripts started...         "
echo "-----------------------------------------------------"
run_spotless_check
echo "-----------------------------------------------------"
run_detekt_auto_correct
echo "-----------------------------------------------------"
run_detekt
echo "-----------------------------------------------------"
echo "-        Spotless & Detekt scripts finished.        -"
echo "-----------------------------------------------------"
