from flask.ext.wtf import Form
from wtforms import TextField, validators,ValidationError

class EnterInfo(Form):
    Notes1 = TextField(label='Email Address', validators=[validators.required(), validators.Email()])   
    Notes2 = TextField(label ='New Password',validators =[validators.required(),validators.EqualTo('confirm',message ='Passwords must match')])
    confirm = TextField(label ='Repeat Password',validators =[validators.required()])
    Notes3 = TextField(label='Name', validators=[validators.required()]) 
    Notes4 = TextField(label='Address') 

    
class RetrieveInfo(Form):
    user_email = TextField(label='Items to add to DB', description="db_get", validators=[validators.required(), validators.Email()]) 
    pw = TextField(label ='Password', description ="db_get", validators =[validators.required()]) 

class SearchTicket(Form):
    Search1 = TextField(label='Departure_station', validators=[validators.required()]) 
    Search2 = TextField(label='Arrival_station', validators=[validators.required()]) 
    Search3 = TextField(label='Departure_date', validators=[validators.required()]) 
    Search4 = TextField(label='Departure_time', validators=[validators.required()]) 
    Search5 = TextField(label='Ticket_type') 
    Search6 = TextField(label='exact_time') 
    Search7 = TextField(label='connection') 