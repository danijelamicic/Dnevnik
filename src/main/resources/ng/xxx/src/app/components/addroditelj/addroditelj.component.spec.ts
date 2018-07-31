import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddroditeljComponent } from './addroditelj.component';

describe('AddroditeljComponent', () => {
  let component: AddroditeljComponent;
  let fixture: ComponentFixture<AddroditeljComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddroditeljComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddroditeljComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
