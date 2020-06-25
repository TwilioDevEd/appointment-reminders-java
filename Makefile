.PHONY: serve

serve:
	mvn compile exec:java -Dexec.mainClass="com.twilio.appointmentreminders.Server"
