import { Odeljenje } from './odeljenje';
import { Razred } from './razred';
import { Roditelj } from './roditelj';
import { EKorisnikUloga } from '../enum/ekorisnik-uloga.enum';
import { Ocena } from './ocena';


export class Ucenik {
    id: Number;
    ime: string;
    prezime: string;
    korisnickoIme: string;
    email: string;
    lozinka: string;
    uloga: EKorisnikUloga;
    odeljenje: Odeljenje;
    roditelj: Roditelj;
    ocena: Ocena [];

}
