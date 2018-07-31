import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { FormroditeljComponent } from './formroditelj.component';

describe('FormroditeljComponent', () => {
  let component: FormroditeljComponent;
  let fixture: ComponentFixture<FormroditeljComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FormroditeljComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(FormroditeljComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
