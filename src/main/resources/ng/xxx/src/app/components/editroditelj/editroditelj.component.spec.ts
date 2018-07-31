import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditroditeljComponent } from './editroditelj.component';

describe('EditroditeljComponent', () => {
  let component: EditroditeljComponent;
  let fixture: ComponentFixture<EditroditeljComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditroditeljComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditroditeljComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
