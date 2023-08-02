import { Directive, ElementRef, HostListener, Input } from '@angular/core';

@Directive({
  selector: '[inputRestriction]'
})
export class InputRestrictionDirective {

  @Input('inputRestriction') InputRestrictionDirective!: string;

  private element: ElementRef;

  constructor(element : ElementRef) {
    this.element = element;
  }

  @HostListener('keypress', ['$event'])
  handleKeyPress(event: KeyboardEvent)
  {
    var regex = new RegExp(this.InputRestrictionDirective);
    var str = String.fromCharCode(!event.charCode ? event.which : event.charCode);
    if (regex.test(str)) {
        return true;
    }

    event.preventDefault();
    return false;
  }

}
