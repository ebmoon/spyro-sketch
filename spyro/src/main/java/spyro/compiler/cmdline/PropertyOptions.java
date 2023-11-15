package spyro.compiler.cmdline;

import sketch.util.cli.CliAnnotatedOptionGroup;
import sketch.util.cli.CliParameter;

public class PropertyOptions extends CliAnnotatedOptionGroup {
	public PropertyOptions() {
		super("prop", "options for synthesized properties");
	}
	
	@CliParameter(help = "The number of disjunct at most")
	public int numAtomMax = 3;
	
	@CliParameter(help = "Disable minimization of synthesized properties")
	public boolean disableMinimization = false;
}
