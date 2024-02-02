PARSER := java -jar ~/antlr-4.13.1-complete.jar

clean-parser:
	rm src/main/java/spyro/compiler/parser/Spyro*

parser:
	cd src/main/antlr/spyro/compiler/parser && $(PARSER) Spyro.g4 -o ../../../../java/spyro/compiler/parser/ -visitor -no-listener
