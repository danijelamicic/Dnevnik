import { Odeljenje } from './odeljenje';
import { Predaje } from './predaje';
import { Ocena } from './ocena';

export class PredajeUOdeljenju {
    idPredajeUOdeljenju: Number;
    predaje: Predaje;
    odeljenje: Odeljenje;
    ocene: Ocena [];
}
