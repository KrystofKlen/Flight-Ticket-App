import { Component, Inject } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Passanger } from 'src/app/Passanger';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent {

  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  emailGiven = "";

  constructor( public dialogRef: MatDialogRef<UpdateUserComponent> ) {
    
  }

  closeAndSendResult(){
    this.dialogRef.close({email:this.emailGiven,updateButtonClicked:true});
  }

  

}
