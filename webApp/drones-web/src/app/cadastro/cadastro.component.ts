import { Component, OnInit } from '@angular/core';
import { CadastroService, IAllDrones, INewDrone } from './cadastro.service';
import { FormGroup, Validators } from '@angular/forms';
import { FormBuilder } from '@angular/forms';
import { MessageService } from 'primeng/api';

interface Status {
  status_code: number,
  status: string
}
@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.css'],
  providers: [MessageService]
})

export class CadastroComponent implements OnInit {
  form!: FormGroup;

  droneSource: IAllDrones[] = [];
  newDrone: INewDrone = {} as INewDrone
  uploadedFiles: any[] = [];

  status: Status[] = [];
  selectedstatus!: string;


  constructor(
    private cadastroService: CadastroService,
    private formBuilder: FormBuilder,
    private messageService: MessageService
  ) {
    this.status = [
      {
        status_code: 1, status: 'Created'
      },
      {
        status_code: 2, status: 'processing'
      },
      {
        status_code: 3, status: 'finished'
      },
    ]
  }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      nomeDrone: [null, Validators.required],
      latitudeDrone: [null, Validators.required],
      longitudeDrone: [null, Validators.required],
      temperatura: [null, Validators.required],
      umidade: [null, Validators.required],
      rastreando: [null, Validators.required]
    });
    this.listAllDrones();
  }


  listAllDrones() {
    this.cadastroService
      .listarTodosDrones()
      .subscribe((resp) => {
        this.droneSource = resp;
      });

  }

  inputDrones() {
    this.newDrone.nomeDrone = this.form.value.nomeDrone
    this.newDrone.latitudeDrone = this.form.value.latitudeDrone
    this.newDrone.longitudeDrone = this.form.value.longitudeDrone
    this.newDrone.temperatura = this.form.value.temperatura
    this.newDrone.umidade = this.form.value.umidade
    this.newDrone.rastreando = this.form.value.rastreando
    this.cadastroService
      .cadastrarDrone(this.newDrone)
      .subscribe(() => {
        this.messageService.add({ severity: 'success', summary: 'Feito!', detail: 'Drone Criado!' });
        this.form.reset();
        this.listAllDrones();
      }
      );

  }

  // alteraPagamento(pagamento: IAllPayments, new_status_code: number ) {
  //   console.log(pagamento)
  //   this.cadastroService
  //     .updatePagamento(pagamento.payment_id, new_status_code)
  //     .subscribe(() => {
  //       this.messageService.add({ severity: 'success', summary: 'Feito!', detail: 'Pagamento Alterado' });
  //       this.listAllUsers();
  //     }
  //     )
  // }


}
