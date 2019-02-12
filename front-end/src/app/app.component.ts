import { Component, OnInit } from '@angular/core';
import { ParqueaderoService } from './services/parqueadero.service';
import { Vehiculo } from './models/vehiculo';
import { IndexService } from './services/index.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  providers: [ParqueaderoService]
})
export class AppComponent implements OnInit {
  public title = 'ADN Ceiba Software House';
  public vehiculos: Vehiculo[];
  public vehiculoDto: Vehiculo;
  public mensajeError: string;

  public errorCargaVehiculos = 'No se puede actualizar información de parqueadero a momento';
  public errorIngreso = 'El vehiculo no se encuentra autorizado para ingresar, verificar información';
  public errorRetiroVehiculo = 'El vehiculo no esta autorizado para salir por pagos pendientes o ya había sido retirado del parqueadero';
  constructor(
    private _parqueaderoService: ParqueaderoService,
   ) {}

  ngOnInit() {
      this.obtenerVehiculosEnParqueadero();
  }

  obtenerVehiculosEnParqueadero() {
    this.mensajeError = '';
    this._parqueaderoService.obtenerVehiculosParqueadero().subscribe(
      response => {
        //console.log(response);
        this.vehiculos = response;
      },
      error => {
        const errorMensaje = <any> error;
        //console.log(errorMensaje);
        if (errorMensaje != null) {
          this.mensajeError = this.errorCargaVehiculos;
        }
      }
    );

  }

}

