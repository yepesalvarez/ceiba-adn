import { Component, OnInit } from '@angular/core';
import { ParqueaderoService } from './services/parqueadero.service';
import { Vehiculo } from './models/vehiculo';
import { CobroService } from './services/cobro.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  providers: [ParqueaderoService, CobroService]
})
export class AppComponent implements OnInit {
  public title = 'ADN Ceiba Software House';
  public vehiculos: Vehiculo[];
  public vehiculoDto: Vehiculo;
  public mensajeError: string;
  public mensajeCobrar: string;
  public mensaje: string;
  public errorCilindraje = 'Se debe ingresar el cilindraje de la moto';
  public errorTipoVehiculoNoSeleccionado = 'Se debe seleccionar un tipo de vehículo';
  public errorCargaVehiculos = 'No se puede actualizar información de parqueadero a momento';
  public errorIngreso = 'El vehiculo no se encuentra autorizado para ingresar, verificar si ya esta ingresado o incumple pico y placa';
  public errorRetiroVehiculo = 'El vehiculo no esta autorizado para salir por pagos pendientes o ya fué retirado';
  constructor(
    private parqueaderoService: ParqueaderoService,
    private cobroService: CobroService
   ) {}

  ngOnInit() {
    this.vehiculoDto = new Vehiculo(null, null, null, null, null);
    this.obtenerVehiculosEnParqueadero();
  }

  obtenerVehiculosEnParqueadero() {
    this.limpiarMensajes();
    this.parqueaderoService.obtenerVehiculosParqueadero().subscribe(
      response => {
        this.vehiculos = response;
      },
      error => {
        console.log(error);
        const errorMensaje = error as any;
        if (errorMensaje != null) {
          this.mensajeError = errorMensaje;
        }
      }
    );
  }

  seleccionarMoto() {
    this.vehiculoDto.tipoVehiculo = 'moto';
    document.getElementById('cilindraje-div').hidden = false;
  }

  seleccionarCarro() {
    this.vehiculoDto.tipoVehiculo = 'carro';
    document.getElementById('cilindraje-div').hidden = true;
  }

  ingresarVehiculo() {
    this.limpiarMensajes();
    if (this.vehiculoDto.tipoVehiculo == null) {
      this.mensajeError = this.errorTipoVehiculoNoSeleccionado;
    } else {
      if (this.vehiculoDto.tipoVehiculo === 'moto' && this.vehiculoDto.cilindraje == null) {
        this.mensajeError = this.errorCilindraje;
      } else {
        this.parqueaderoService.ingresarVehiculo(this.vehiculoDto).subscribe(
          response => {
          this.vehiculoDto.tipoVehiculo = null;
          this.vehiculos = response;
          }, error => {
            const errorMensaje = error as any;
            if (errorMensaje != null) {
              this.mensajeError = this.errorIngreso;
            }
          });
        }
      }
  }

  cobrarParqueo(idVehiculo: number) {
    this.limpiarMensajes();
    const fechaHoraActual = this.calcularFechaHoraActual();
    this.cobroService.obtenerValorCobro(idVehiculo, fechaHoraActual).subscribe(
      response => {
        this.mensajeCobrar = response;
      },
      error => {
        console.log(error);
        const errorMensaje = error as any;
        if (errorMensaje != null) {
          this.mensajeError = errorMensaje;
        }
      }
    );
  }

  pagarParqueo(idVehiculo: number) {
    this.limpiarMensajes();
    this.parqueaderoService.pagarParqueadero(idVehiculo).subscribe(
      response => {
        this.mensaje = response.toString();
      },
      error => {
        console.log(error);
        const errorMensaje = error as any;
        if (errorMensaje != null) {
          this.mensajeError = errorMensaje;
        }
      }
    );
  }

  retirarVehiculo(idVehiculo: number){
    this.limpiarMensajes();
    this.parqueaderoService.retirarVehiculo(idVehiculo).subscribe(
      response => {
        this.vehiculos = response;
      }, error => {
        const errorMensaje = error as any;
        if(errorMensaje != null) {
          this.mensajeError = this.errorRetiroVehiculo;
        }
      }
    );
  }

  limpiarMensajes() {
    this.mensajeError = '';
    this.mensajeCobrar = '';
    this.mensaje = '';
  }

  calcularFechaHoraActual(): string {

    const date = new Date();
    const yyyy = date.getFullYear();
    const gg = date.getDate();
    const mm = (date.getMonth() + 1);

    let dia = '';
    if (gg < 10) {
       dia = '0' + gg.toString();
    } else {
      dia = gg.toString();
    }
    let mes = '';
    if (mm < 10) {
        mes = '0' + mm.toString();
    } else {
        mes = mm.toString();
    }

    const hh = date.getHours();
    const minutes = date.getMinutes();
    const seconds = date.getSeconds();

    let horas = '';
    if (hh < 10) {
      horas = '0' + hh.toString();
    } else {
      horas = hh.toString();
    }
    let mins = '';
    if (minutes < 10) {
        mins = '0' + minutes.toString();
    } else {
      mins = minutes.toString();
    }
    let ss = '';
    if (seconds < 10){
      ss = '0' + seconds;
    } else {
      ss = seconds.toString();
    }

    return (yyyy + '-' + mes + '-' + dia + ' ' + horas + ':' + mins);

}

}