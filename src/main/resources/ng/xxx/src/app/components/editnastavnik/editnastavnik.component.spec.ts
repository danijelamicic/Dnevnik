import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EditnastavnikComponent } from './editnastavnik.component';

describe('EditnastavnikComponent', () => {
  let component: EditnastavnikComponent;
  let fixture: ComponentFixture<EditnastavnikComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EditnastavnikComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EditnastavnikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
