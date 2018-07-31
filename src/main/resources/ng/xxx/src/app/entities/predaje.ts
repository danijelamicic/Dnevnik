import { Nastavnik } from './nastavnik';
import { Predmet } from './predmet';
import { PredajeUOdeljenju } from './predaje-uodeljenju';

export class Predaje {
    idPredaje: Number;
    nastavnik: Nastavnik;
    predmet: Predmet;
    predajeUOdeljenju: PredajeUOdeljenju [];
}
