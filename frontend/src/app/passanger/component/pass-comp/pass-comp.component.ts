import { Component, Injectable, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { last } from 'rxjs';
import { Passanger } from 'src/app/Passanger';
import { PassServService } from '../../service/pass-serv.service';
import { DialogAddNewPersonComponent } from '../dialog-add-new-person/dialog-add-new-person.component';
import { UpdateUserComponent } from '../update-user/update-user.component';


@Injectable({
  providedIn: 'root'
})
@Component({
  selector: 'app-pass-comp',
  templateUrl: './pass-comp.component.html',
  styleUrls: ['./pass-comp.component.css']
})
export class PassCompComponent implements OnInit{

  deletePerson(row:Passanger) {
    this.passService.deletePerson(row);
    window.location.reload();
  }

  setPassangersData(){
    this.passService.getPassangers()
    .subscribe(data => this.dataSource = data)
  }
  
  /**
   * 
   * @param pass Passanger to post
   */
  postNewPassanger(pass:Passanger){
    console.log("POSTNG",pass);
    this.passService.postPassanger(pass)
    .subscribe({
      error:(err) => alert("This username already in use."),
      complete(){
        window.location.reload();
      }
    });
    
  }

  ngOnInit(): void {
    this.setPassangersData();
  }

  constructor(private passService:PassServService, public dialog: MatDialog){}

  /**
   * Opens dialog for posting a new user
   */
  openDialogPost() {
    const dialogRef = this.dialog.open(DialogAddNewPersonComponent);
  }

  /**
   * Opens dialog for updating passangers info (email), id can not be updated
   * @param person person to be edited
   */
  openDialogPatch(person:Passanger) {
    console.log(person);
    const dialogRef = this.dialog.open(UpdateUserComponent,{data:{data:person}});

    dialogRef.afterClosed().subscribe(result => {
      if(result.updateButtonClicked){
        person.email=result.email;
        this.passService.updatePerson(person);
        window.location.reload();
      }
    });
  }

  /**
   * Goes to user profile with all his bookings.
   * @param obj 
   */
  goToProfile(obj:Passanger){
    console.log(obj.personId);
    this.passService.navigateToProfile(obj.personId);
    
  }

  displayedColumns: string[] = ['personId','email','edit','bookings','delete'];
  dataSource:Passanger[] = [];
  clickedRows = new Set<Passanger>();
}

