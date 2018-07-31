import { Ucenik } from './ucenik';
import { PredajeUOdeljenju } from './predaje-uodeljenju';

export class Ocena {
    idOcena: Number;
    vrednost: Number;
    datum: string;
    polugodiste: Number;
    predajeUOdeljenju: PredajeUOdeljenju;
    ucenik: Ucenik;


}
