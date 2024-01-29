package spyro.synthesizer;

public class Synthesizer {
//    public PropertySynthesizer() {
//
//    }
    public Example checkSoundness(Property phi) {
        return null;
    }

    public Property synthesize(ExampleSet pos, ExampleSet negMust, ExampleSet negMay) {
        return null;
    }

    public Example checkPrecision(Property phi, ExampleSet pos, ExampleSet negMust, ExampleSet negMay, Property phiPrime) {
        return null;
    }
    public Property synthesizeProperty(Property phiInit, ExampleSet pos, ExampleSet negMust, ExampleSet negMay) {
        Property phiE = phiInit;
        Property phiLastSound = null;

        while(true) {
           Example ePos = checkSoundness(phiE);
            if (ePos != null) {
                pos.add(ePos);
                Property phiPrime = synthesize(pos, negMust, negMay);
                if (phiPrime != null) {
                    phiE = phiPrime;
                }
                else {
                    phiE = phiLastSound;
                    negMay.clear();
                }
            }
            else {
                negMust.merge(negMay);
                negMay.clear();
                phiLastSound = phiE;
                Property phiPrime = null;
                Example eNeg = checkPrecision(phiE, pos, negMust, negMay, phiPrime);
                if (eNeg == null) {
                    return phiE;
                }
                else {
                    negMay.add(eNeg);
                    phiE = phiPrime;
                }

            }
        }

    }
}
