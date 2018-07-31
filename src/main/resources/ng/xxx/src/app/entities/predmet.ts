import { Predaje } from './predaje';
import { PredmetURazredu } from './predmet-urazredu';

export class Predmet {
    idPredmet: Number;
    nazivPredmeta: string;
    predaje: Predaje [];
    predmetURazredu: PredmetURazredu [];
}
