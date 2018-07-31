
import { Ucenik } from './ucenik';
import { PredajeUOdeljenju } from './predaje-uodeljenju';
import { Razred } from './razred';

export class Odeljenje {
    idOdeljenje: Number;
    vrednost: Number;
    razred: Razred = new Razred();
    predajeUOdeljenju: PredajeUOdeljenju [];
    ucenici: Ucenik [];
}
