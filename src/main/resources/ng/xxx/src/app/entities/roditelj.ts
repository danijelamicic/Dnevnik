import { EKorisnikUloga } from '../enum/ekorisnik-uloga.enum';
import { Ucenik } from './ucenik';

export class Roditelj {
    id: Number;
    ime: string;
    prezime: string;
    korisnickoIme: string;
    email: string;
    lozinka: string;
    uloga: EKorisnikUloga;
    ucenici: Ucenik [];

}
