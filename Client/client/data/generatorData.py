import random

firstInsert = "INSERT INTO users (full_name, email, gender, state, birth_year) VALUES (\'Toto"
secondInsert = "\', \'bobjones@gmail.com\', \'M\', \'TX\', 1975);\n----\n"
with open("dataGenInsert.cql", "w") as file:
	file.write("USE demo;\n----\n")
	for x in range(1,100):
		file.write("%s%i%s" % (firstInsert, x, secondInsert))
firstRead = "SELECT * FROM users WHERE full_name=\'Toto"
secondRead = "\';\n----\n"
with open("dataGenRead.cql", "w") as file:
	file.write("USE demo;\n----\n")
	for x in range(1,1000):
		file.write("%s%i%s" % (firstRead, random.randint(1,99), secondRead))
