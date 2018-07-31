import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AddnastavnikComponent } from './addnastavnik.component';

describe('AddnastavnikComponent', () => {
  let component: AddnastavnikComponent;
  let fixture: ComponentFixture<AddnastavnikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AddnastavnikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AddnastavnikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
