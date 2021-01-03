


run:
	chmod 755 compile.sh 
	chmod 755 run.sh 
	./compile.sh 
	./run.sh 

clean:
	rm -rf *.class 
	rm -rf musicHub/*.class 
	rm -rf musicHub/util/*.class 
