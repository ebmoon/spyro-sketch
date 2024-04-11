package spyro.synthesis;

import sketch.compiler.ast.core.Function;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.Map;

public class RunningResults implements Comparable<RunningResults>{
    PropertySet properties;
    Map<String, Function> lambdaFunctions;
    BigInteger grammarSize;
    long runningTime;
    boolean isUnder;
    long timeSoundness;
    int numSoundness;
    long timePrecision;
    int numPrecision;
    long timeSynthesis;
    int numSynthesis;
    int numHiddenWitness;


    public BigInteger getGrammarSize() {
        return grammarSize;
    }

    public long getRunningTime() {
        return runningTime;
    }

    public RunningResults(boolean isUnder, PropertySet properties, Map<String, Function> lambdaFunctions, BigInteger grammarSize, long runningTime, long timeSoundness, int numSoundness, long timePrecision, int numPrecision, long timeSynthesis, int numSynthesis, int numHiddenWitness) {
        this.isUnder = isUnder;
        this.properties = properties;
        this.lambdaFunctions = lambdaFunctions;
        this.grammarSize = grammarSize;
        this.runningTime = runningTime;
        this.timeSoundness = timeSoundness;
        this.numSoundness = numSoundness;
        this.timePrecision = timePrecision;
        this.numPrecision = numPrecision;
        this.timeSynthesis = timeSynthesis;
        this.numSynthesis = numSynthesis;
        this.numHiddenWitness = numHiddenWitness;
    }

    @Override
    public int compareTo(RunningResults other) {
        return Long.compare(this.runningTime, other.runningTime);
    }

    public String toString() {
        String str = "";
        int idx = 0;
        for (Property prop : properties.getProperties()) {
            String code = prop.toSketchCode().getBody().toString();

            str += "Property " + idx++ + "\n" + code + "\n";

            for (Map.Entry<String, Function> entry : lambdaFunctions.entrySet()) {
                String key = entry.getKey();
                if (code.contains(key)) {
                    str += entry.getValue() + "\n";
                    str += entry.getValue().getBody().toString() + "\n";
                }
            }
        }

        str += "Total time = " + runningTime + "\n";
        str += "Grammar Size = " + grammarSize + "\n";
        str += "Soundness Number = " + numSoundness + "\n";
        str += "Soundness Time = " + timeSoundness + "\n";
        str += "Precision Number = " + numPrecision + "\n";
        str += "Precision Time = " + timePrecision + "\n";
        str += "Synthesis Number = " + numSynthesis + "\n";
        str += "Synthesis Time = " + timeSynthesis + "\n";
        str += "Max Number of Hidden Witness = " + numHiddenWitness + "\n";
        return str;
    }

    public String toCSV() {
        BigDecimal sizeDecimal = new BigDecimal(grammarSize);
        DecimalFormat df = new DecimalFormat("0.######E0");
        return String.format("%s, %d, %d, %d, %d, %d, %d, %d, %d", df.format(sizeDecimal), runningTime, numSoundness, timeSoundness, numPrecision, timePrecision, numSynthesis, timeSynthesis, numHiddenWitness);
    }


}