import { Component, inject, OnInit } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NxWelcomeComponent } from './nx-welcome.component';
import { FaIconLibrary, FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from './shared/font-awesome-icons';
import { NavbarComponent } from "./layout/navbar/navbar.component";
import { FooterComponent } from "./layout/footer/footer.component";

@Component({
  standalone: true,
  imports: [
    NxWelcomeComponent,
    RouterModule,
    FontAwesomeModule,
    NavbarComponent,
    FooterComponent
],
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss',
})
export class AppComponent implements OnInit{
  private faIconLibrary= inject(FaIconLibrary);

  ngOnInit(): void {
    this.initFontAwesome();
  }
 
  initFontAwesome() {
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }
}
