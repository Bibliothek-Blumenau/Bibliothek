import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';

@Component({
  selector: 'loading-spinner',
  templateUrl: './loading-spinner.component.html',
  styleUrls: ['./loading-spinner.component.css']
})
export class LoadingSpinnerComponent implements OnChanges {

  @Input() visible = false;
  showSpinner = false;

  private readonly DELAY = 500;
  private timeout?: ReturnType<typeof setTimeout>;

  ngOnChanges(changes: SimpleChanges): void {
    if (!changes['visible']) return;

    clearTimeout(this.timeout);
    this.showSpinner = false;

    if (this.visible) {
      this.timeout = setTimeout(() => {
        this.showSpinner = true;
      }, this.DELAY);
    }
  }
}
