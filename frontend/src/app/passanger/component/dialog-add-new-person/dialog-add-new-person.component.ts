import { Component, Inject } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import { PassServService } from '../../service/pass-serv.service';
import { Passanger } from 'src/app/Passanger';
import { PassCompComponent } from '../pass-comp/pass-comp.component';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { lastValueFrom } from 'rxjs';

@Component({
  selector: 'app-dialog-add-new-person',
  templateUrl: './dialog-add-new-person.component.html',
  styleUrls: ['./dialog-add-new-person.component.css']
})
export class DialogAddNewPersonComponent {

  constructor(private componentPers:PassCompComponent ){
     
  }

  email= new FormControl('', [Validators.required, Validators.email]);
  usermail:string = "";
  newPassanger:Passanger = {personId:-1,email:""};

  getErrorMessage() {
    if (this.email.hasError('required')) {
      return 'You must enter a value';
    }
    return this.email.hasError('email') ? 'Not a valid email' : '';
  }
  
  postPerson(){
    console.log("posting")
    console.log(this.usermail);
    this.newPassanger.email=this.usermail;
    this.componentPers.postNewPassanger(this.newPassanger);

  }

  
}
