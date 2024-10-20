import { Component, inject, OnInit, PLATFORM_ID } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NxWelcomeComponent } from './nx-welcome.component';
import { FaIconLibrary, FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { fontAwesomeIcons } from './shared/font-awesome-icons';
import { NavbarComponent } from "./layout/navbar/navbar.component";
import { FooterComponent } from "./layout/footer/footer.component";
import { isPlatformBrowser } from '@angular/common';

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

  plateformID= inject(PLATFORM_ID);

  constructor() {
    if (isPlatformBrowser(this.plateformID)) {
    }
  }

  ngOnInit(): void {
    this.initFontAwesome();
  }
 
  initFontAwesome() {
    this.faIconLibrary.addIcons(...fontAwesomeIcons);
  }
}
