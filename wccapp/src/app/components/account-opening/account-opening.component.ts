import {Component, OnInit} from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { filter, map, mergeMap } from 'rxjs';

@Component({
  selector: 'app-account-opening',
  templateUrl: './account-opening.component.html',
  styleUrl: './account-opening.component.less'
})
export class AccountOpeningComponent implements OnInit {
  currentStep: number = 0;

  constructor(private router: Router, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.setCurrentStep();
  }

  setCurrentStep(): void {
    const storedStep = localStorage.getItem('currentStep');
    if (storedStep !== null) {
      this.currentStep = +storedStep;
    }

    this.router.events.pipe(
      filter(event => event instanceof NavigationEnd),
      map(() => this.route.firstChild),
      filter(route => route !== null),
      mergeMap(route => route!.data)
    ).subscribe(data => {
      // Update currentStep based on route data
      switch (data['step']) {
        case 'uk-postcode':
          this.currentStep = 1;
          break;
        default:
          this.currentStep = 0; // Default to account-info
          break;
      }
      localStorage.setItem('currentStep', this.currentStep.toString());
    });
  }
}
