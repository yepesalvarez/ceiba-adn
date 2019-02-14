import { Injectable } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';
import 'rxjs/add/operator/map';
import { GLOBAL } from './global';
import { Vehiculo } from '../models/vehiculo';

@Injectable()
export class ParqueaderoService {
    public url: string;
    constructor(private _http: Http){
        this.url = GLOBAL.url_parqueadero;
    }

    public obtenerVehiculosParqueadero() {
        return this._http.get(this.url).map(res => res.json());
    }

    ingresarVehiculo(vehiculoDto: Vehiculo) {
        return this._http.post(this.url, vehiculoDto).map(res => res.json());
    }

    retirarVehiculo(idVehiculo: number) {
        return this._http.delete(`${this.url}/${idVehiculo}`).map(response => response.json());
    }

    pagarParqueadero(idVehiculo: number) {
        const params = new URLSearchParams();
        params.append('idVehiculo', idVehiculo.toString());
        return this._http.patch(this.url, params).map(response => response.json());
    }

}
