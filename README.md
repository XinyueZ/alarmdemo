alarmdemo
=========

Demo to show how to use AlarmManager and maybe AlarmClock.

1. Use the intent to catch all ACTION_TIME_TICK events to demo every minute.
2. Use AlarmManager (ELAPSED_REALTIME_WAKEUP) to demo every minute.
3. Use AlarmManager (ELAPSED_REALTIME_WAKEUP) to demo alarming what user inputs.
4. Because of the inexact setting by 4.4.x, set, setRepeat etc, and the setExact would be the only method to do a
"near exact" (setWindow as well) at 4.4.x. We change to use set and setExact to do manual repeat instead of calling
setRepeat.