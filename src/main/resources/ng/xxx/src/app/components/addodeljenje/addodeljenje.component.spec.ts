import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddodeljenjeComponent } from './addodeljenje.component';

describe('AddodeljenjeComponent', () => {
  let component: AddodeljenjeComponent;
  let fixture: ComponentFixture<AddodeljenjeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddodeljenjeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddodeljenjeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
