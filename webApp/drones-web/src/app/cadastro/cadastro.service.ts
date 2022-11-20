import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface IAllDrones {
  idDrone: number;
  nomeDrone: string;
  latitudeDrone: number;
  longitudeDrone: number;
  temperatura: number;
  umidade: number;
  rastreando: boolean;
}


export interface INewDrone {
  nomeDrone: string;
  latitudeDrone: number;
  longitudeDrone: number;
  temperatura: number;
  umidade: number;
  rastreando: boolean;
}



@Injectable({
  providedIn: 'root',
})

export class CadastroService {
  pathUrlBase = 'http://localhost:8080'
  constructor(private http: HttpClient) { }



  listarTodosDrones(): Observable<IAllDrones[]> {
    return this.http.get<IAllDrones[]>(
      `${this.pathUrlBase}/getDrone/drones`
    );
  }




  cadastrarDrone(
    newDrone: INewDrone
  ): Observable<IAllDrones> {
    return this.http.post<IAllDrones>(
      `${this.pathUrlBase}/putDrones`,
      { ...newDrone }
    );
  }

  // updatePagamento(
  //   payment_id: number,
  //   new_status_code: number
  // ): Observable<IAllPayments> {
  //   console.log(payment_id)
  //   return this.http.patch<IAllPayments>(
  //     `${this.pathUrlBase}/payment`,
  //     { payment_id, new_status_code }
  //   );
  // }



}



