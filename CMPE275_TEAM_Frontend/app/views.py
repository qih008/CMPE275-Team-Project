from flask import render_template, jsonify, request, url_for, redirect, session
from app import app
from .forms import EnterInfo, RetrieveInfo, SearchTicket
import json
import requests
import ast
from datetime import timedelta
from flask_mail import Mail,  Message

app.config.update(
    MAIL_SERVER = 'smtp.gmail.com',
    MAIL_PORT = 465,
    MAIL_USE_SSL = True,
    MAIL_USERNAME = 'sjsu.cmpe273.test@gmail.com',
    MAIL_PASSWORD = 'sjsu1234',
)
mail = Mail(app)

# index view function suppressed for brevity
@app.route('/', methods=['GET', 'POST'])
@app.route('/index', methods=['GET', 'POST'])
def index():
    #r = requests.get("http://ec2-54-67-125-74.us-west-1.compute.amazonaws.com:8080/player/4")
    #r = requests.post("http://localhost:8080/user?name=333&email=333@gmail.com&password=1234")
    #r = requests.get("http://localhost:8080/user/222")
    #return json.loads(r.text)['email']
    #json.loads(r.text)['firstname']
    form1 = EnterInfo(request.form)
    form2 = RetrieveInfo(request.form)
    
    if request.method == 'POST' and form1.validate():   
        user_email = str(form1.Notes1.data)
        password = str(form1.Notes2.data)
        name = str(form1.Notes3.data)
        address = str(form1.Notes4.data)
        url = "http://localhost:8080/user"
        params = {'email':user_email, 'password':password, 'name':name, 'street':address}
        result = requests.post(url, params=params)
        if result.status_code == 200:
            session['email'] = user_email
            return redirect(url_for('search'))
        #print result.url, result.status_code
        else:
            return render_template('index.html', form1=form1, form2=form2)



    if request.method == 'POST' and form2.validate():   
        user_email = str(form2.user_email.data)
        password = str(form2.pw.data)
        url = "http://localhost:8080/user/" + user_email + "/" + password
        result = requests.get(url)
        print result.content
        if result.content == "true":
            print "login successfully"
            session['email'] = user_email
            return redirect(url_for('search'))
        else:
            print "login failed"
            return render_template('index.html', form1=form1, form2=form2)

    return render_template('index.html', form1=form1, form2=form2)


@app.route('/search', methods=['GET', 'POST'])
def search():

    form3 = SearchTicket(request.form)
    #print session.get('email', None)

    if request.method == 'POST' and form3.validate():
        departure_station = str(form3.Search1.data)
        arrival_station = str(form3.Search2.data)
        departure_time = str(form3.Search4.data)
        departure_date = str(form3.Search3.data)
        ticket_type = str(form3.Search5.data)
        exact_time = str(form3.Search6.data)
        connection = str(form3.Search7.data)

        if not connection:
            connection = 2
        elif connection == "Any":
            connection = 2
        elif connection == "None":
            connection = 0
        elif connection == "One":
            connection = 1

        if not ticket_type:
            ticket_type = "Any"

        if not exact_time:
            exact_time = "false"

        params = {'departure_station':departure_station, 'arrival_station':arrival_station, 'departure_time':departure_time, 'departure_date':departure_date, 'type':ticket_type, 'exact_time':exact_time, 'connection':connection}
        result = requests.get("http://localhost:8080/searchTicket", params=params)
        session['params'] = params

        #print result.url
        #result = len(json.loads(r.text))
        #print result1['train_name'], result1['departure_station'], result1['departure_time'], result1['arrival_station'], result1['arrival_time'], result1['price']
        if result.status_code == 200:
            if len(json.loads(result.text)) == 5:

                # option 1
                result1 = json.loads(result.text)[0]
                if str(result1['transfer']) == "True":
                    result1_string = "Train name is: " + result1['train_name'] + " , departure_station is: " + result1['departure_station'] + ", departure_time is: " + result1['departure_time'] + ", arrival_station is: " + result1['arrival_station'] + ", arrive time is: " + result1['arrival_time'] + ", price is: " + str(result1['price'])
                    total_price1 = result1['price']
                    length = len(result1['transfer_train'])
                    if length == 1:
                        temp_train = result1['transfer_train'][0]
                        result1_string += " , Transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price1 += temp_train['price']
                    else:
                        temp_train = result1['transfer_train'][0]
                        result1_string += " , First transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price1 += temp_train['price']
                        temp_train = result1['transfer_train'][1]
                        result1_string += " , Second transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price1 += temp_train['price']

                else:
                    result1_string = "Train name is: " + result1['train_name'] + " , departure_station is: " + result1['departure_station'] + ", departure_time is: " + result1['departure_time'] + ", arrival_station is: " + result1['arrival_station'] + ", arrive time is: " + result1['arrival_time'] + ", price is: " + str(result1['price'])
                    total_price1 = result1['price']

                # option 2
                result2 = json.loads(result.text)[1]
                if str(result2['transfer']) == "True":
                    result2_string = "Train name is: " + result2['train_name'] + " , departure_station is: " + result2['departure_station'] + ", departure_time is: " + result2['departure_time'] + ", arrival_station is: " + result2['arrival_station'] + ", arrive time is: " + result2['arrival_time'] + ", price is: " + str(result2['price'])
                    total_price2 = result2['price']
                    length = len(result2['transfer_train'])
                    if length == 1:
                        temp_train = result2['transfer_train'][0]
                        result2_string += " , Transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price2 += temp_train['price']
                    else:
                        temp_train = result2['transfer_train'][0]
                        result2_string += " , First transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price2 += temp_train['price']
                        temp_train = result2['transfer_train'][1]
                        result2_string += " , Second transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price2 += temp_train['price']
                else:
                    result2_string = "Train name is: " + result2['train_name'] + " , departure_station is: " + result2['departure_station'] + ", departure_time is: " + result2['departure_time'] + ", arrival_station is: " + result2['arrival_station'] + ", arrive time is: " + result2['arrival_time'] + ", price is: " + str(result2['price'])
                    total_price2 = result2['price']


                # option 3
                result3 = json.loads(result.text)[2]
                if str(result3['transfer']) == "True":
                    result3_string = "Train name is: " + result3['train_name'] + " , departure_station is: " + result3['departure_station'] + ", departure_time is: " + result3['departure_time'] + ", arrival_station is: " + result3['arrival_station'] + ", arrive time is: " + result3['arrival_time'] + ", price is: " + str(result3['price'])
                    total_price3 = result3['price']
                    length = len(result3['transfer_train'])
                    if length == 1:
                        temp_train = result3['transfer_train'][0]
                        result3_string += " , Transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price3 += temp_train['price']
                    else:
                        temp_train = result3['transfer_train'][0]
                        result3_string += " , First transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price3 += temp_train['price']
                        temp_train = result3['transfer_train'][1]
                        result3_string += " , Second transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price3 += temp_train['price']
                else:
                    result3_string = "Train name is: " + result3['train_name'] + " , departure_station is: " + result3['departure_station'] + ", departure_time is: " + result3['departure_time'] + ", arrival_station is: " + result3['arrival_station'] + ", arrive time is: " + result3['arrival_time'] + ", price is: " + str(result3['price'])
                    total_price3 = result3['price']

                # option 4
                result4 = json.loads(result.text)[3]
                if str(result4['transfer']) == "True":
                    result4_string = "Train name is: " + result4['train_name'] + " , departure_station is: " + result4['departure_station'] + ", departure_time is: " + result4['departure_time'] + ", arrival_station is: " + result4['arrival_station'] + ", arrive time is: " + result4['arrival_time'] + ", price is: " + str(result4['price'])
                    total_price4 = result4['price']
                    length = len(result4['transfer_train'])
                    if length == 1:
                        temp_train = result4['transfer_train'][0]
                        result4_string += " , Transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price4 += temp_train['price']
                    else:
                        temp_train = result4['transfer_train'][0]
                        result4_string += " , First transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price4 += temp_train['price']
                        temp_train = result4['transfer_train'][1]
                        result4_string += " , Second transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price4 += temp_train['price']
                else:
                    result4_string = "Train name is: " + result4['train_name'] + " , departure_station is: " + result4['departure_station'] + ", departure_time is: " + result4['departure_time'] + ", arrival_station is: " + result4['arrival_station'] + ", arrive time is: " + result4['arrival_time'] + ", price is: " + str(result4['price'])
                    total_price4 = result4['price']

                # option 5
                result5 = json.loads(result.text)[4] 
                if str(result5['transfer']) == "True":
                    result5_string = "Train name is: " + result5['train_name'] + " , departure_station is: " + result5['departure_station'] + ", departure_time is: " + result5['departure_time'] + ", arrival_station is: " + result5['arrival_station'] + ", arrive time is: " + result5['arrival_time'] + ", price is: " + str(result5['price'])
                    total_price5 = result5['price']
                    length = len(result5['transfer_train'])
                    if length == 1:
                        temp_train = result5['transfer_train'][0]
                        result5_string += " , Transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price5 += temp_train['price']
                    else:
                        temp_train = result5['transfer_train'][0]
                        result5_string += " , First transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price5 += temp_train['price']
                        temp_train = result5['transfer_train'][1]
                        result5_string += " , Second transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price5 += temp_train['price']
                else:
                    result5_string = "Train name is: " + result5['train_name'] + " , departure_station is: " + result5['departure_station'] + ", departure_time is: " + result5['departure_time'] + ", arrival_station is: " + result5['arrival_station'] + ", arrive time is: " + result5['arrival_time'] + ", price is: " + str(result5['price'])
                    total_price5 = result5['price']

                session['result1'] = result1
                session['result2'] = result2
                session['result3'] = result3
                session['result4'] = result4
                session['result5'] = result5
                session['total_price1'] = total_price1
                session['total_price2'] = total_price2
                session['total_price3'] = total_price3
                session['total_price4'] = total_price4
                session['total_price5'] = total_price5
                return render_template('result.html', result1_string=result1_string, result2_string=result2_string, result3_string=result3_string, result4_string=result4_string, result5_string=result5_string)
            
            elif len(json.loads(result.text)) == 1:
                # option 1
                result1 = json.loads(result.text)[0]
                if str(result1['transfer']) == "True":
                    result1_string = "Train name is: " + result1['train_name'] + " , departure_station is: " + result1['departure_station'] + ", departure_time is: " + result1['departure_time'] + ", arrival_station is: " + result1['arrival_station'] + ", arrive time is: " + result1['arrival_time'] + ", price is: " + str(result1['price'])
                    total_price1 = result1['price']
                    length = len(result1['transfer_train'])
                    if length == 1:
                        temp_train = result1['transfer_train'][0]
                        result1_string += " , Transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price1 += temp_train['price']
                    else:
                        temp_train = result1['transfer_train'][0]
                        result1_string += " , First transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price1 += temp_train['price']
                        temp_train = result1['transfer_train'][1]
                        result1_string += " , Second transfer train name is: "+ temp_train['train_name'] + " , departure_station is: " + temp_train['departure_station'] + ", departure_time is: " + temp_train['departure_time'] + ", arrival_station is: " + temp_train['arrival_station'] + ", arrive time is: " + temp_train['arrival_time'] + ", price is: " + str(temp_train['price'])
                        total_price1 += temp_train['price']

                else:
                    result1_string = "Train name is: " + result1['train_name'] + " , departure_station is: " + result1['departure_station'] + ", departure_time is: " + result1['departure_time'] + ", arrival_station is: " + result1['arrival_station'] + ", arrive time is: " + result1['arrival_time'] + ", price is: " + str(result1['price'])
                    total_price1 = result1['price']

                session['result1'] = result1

                session['total_price1'] = total_price1

                return render_template('result_one.html', result1_string=result1_string)
    

            else:
                return render_template('result_not_find.html')

    return render_template('search.html', form3=form3)


@app.route("/confirmation1", methods=['GET', 'POST'])
def confirmation1():
    
    result_search = session.get('result1', None)
    print session.get('email', None)

    total_price = session.get('total_price1', None)
    #localhost:8080/transaction?user_email=111&price=150
    #localhost:8080/ticket?transaction_id=4&user_name=222&train_name=SB1700 2017-12-25&departure_station=A&arrival_station=Z&departure_time=2000&arrival_time=2100&type=express&price=90
    
    params_transaction = {'user_email':session.get('email', None), 'price': total_price}
    result = requests.post("http://localhost:8080/transaction", params=params_transaction)
    result_transaction = json.loads(result.text)

    if str(result_search['transfer']) == "True":
        params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':result_search['train_name'], 'departure_station':result_search['departure_station'], 'arrival_station':result_search['arrival_station'], 'departure_time':result_search['departure_time'], 'arrival_time':result_search['arrival_time'], 'type':"Express", 'price':result_search['price']}
        result = requests.post("http://localhost:8080/ticket", params=params_ticket)
        result_ticket = json.loads(result.text)
    
        result_ticket_string = "Your train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
        length = len(result_search['transfer_train'])
        if length == 1:
            temp_train = result_search['transfer_train'][0]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
        else:
            temp_train = result_search['transfer_train'][0]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])

            temp_train = result_search['transfer_train'][1]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
    
    else:
        params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':result_search['train_name'], 'departure_station':result_search['departure_station'], 'arrival_station':result_search['arrival_station'], 'departure_time':result_search['departure_time'], 'arrival_time':result_search['arrival_time'], 'type':"Express", 'price':result_search['price']}
        result = requests.post("http://localhost:8080/ticket", params=params_ticket)
        result_ticket = json.loads(result.text)
    
        result_ticket_string = "Your train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])

    send_mail(session.get('email', None), "California Ultra-Speed Rail Train Purchase", result_ticket_string+"\n Your total price in this transaction is: "+str(total_price+1))
    return render_template('confirmation.html', ticket_info = result_ticket_string)    


@app.route("/confirmation2", methods=['GET', 'POST'])
def confirmation2():
    
    result_search = session.get('result2', None)
    print session.get('email', None)

    total_price = session.get('total_price2', None)
    #localhost:8080/transaction?user_email=111&price=150
    #localhost:8080/ticket?transaction_id=4&user_name=222&train_name=SB1700 2017-12-25&departure_station=A&arrival_station=Z&departure_time=2000&arrival_time=2100&type=express&price=90
    
    params_transaction = {'user_email':session.get('email', None), 'price': total_price}
    result = requests.post("http://localhost:8080/transaction", params=params_transaction)
    result_transaction = json.loads(result.text)

    if str(result_search['transfer']) == "True":
        params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':result_search['train_name'], 'departure_station':result_search['departure_station'], 'arrival_station':result_search['arrival_station'], 'departure_time':result_search['departure_time'], 'arrival_time':result_search['arrival_time'], 'type':"Express", 'price':result_search['price']}
        result = requests.post("http://localhost:8080/ticket", params=params_ticket)
        result_ticket = json.loads(result.text)
    
        result_ticket_string = "Your train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
        length = len(result_search['transfer_train'])
        if length == 1:
            temp_train = result_search['transfer_train'][0]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
        else:
            temp_train = result_search['transfer_train'][0]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])

            temp_train = result_search['transfer_train'][1]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
    
    else:
        params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':result_search['train_name'], 'departure_station':result_search['departure_station'], 'arrival_station':result_search['arrival_station'], 'departure_time':result_search['departure_time'], 'arrival_time':result_search['arrival_time'], 'type':"Express", 'price':result_search['price']}
        result = requests.post("http://localhost:8080/ticket", params=params_ticket)
        result_ticket = json.loads(result.text)
    
        result_ticket_string = "Your train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])

    send_mail(session.get('email', None), "California Ultra-Speed Rail Train Purchase", result_ticket_string+"\n Your total price in this transaction is: "+str(total_price+1))
    return render_template('confirmation.html', ticket_info = result_ticket_string)    

@app.route("/confirmation3", methods=['GET', 'POST'])
def confirmation3():
    
    result_search = session.get('result3', None)
    print session.get('email', None)

    total_price = session.get('total_price3', None)
    #localhost:8080/transaction?user_email=111&price=150
    #localhost:8080/ticket?transaction_id=4&user_name=222&train_name=SB1700 2017-12-25&departure_station=A&arrival_station=Z&departure_time=2000&arrival_time=2100&type=express&price=90
    
    params_transaction = {'user_email':session.get('email', None), 'price': total_price}
    result = requests.post("http://localhost:8080/transaction", params=params_transaction)
    result_transaction = json.loads(result.text)

    if str(result_search['transfer']) == "True":
        params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':result_search['train_name'], 'departure_station':result_search['departure_station'], 'arrival_station':result_search['arrival_station'], 'departure_time':result_search['departure_time'], 'arrival_time':result_search['arrival_time'], 'type':"Express", 'price':result_search['price']}
        result = requests.post("http://localhost:8080/ticket", params=params_ticket)
        result_ticket = json.loads(result.text)
    
        result_ticket_string = "Your train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
        length = len(result_search['transfer_train'])
        if length == 1:
            temp_train = result_search['transfer_train'][0]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
        else:
            temp_train = result_search['transfer_train'][0]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])

            temp_train = result_search['transfer_train'][1]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
    
    else:
        params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':result_search['train_name'], 'departure_station':result_search['departure_station'], 'arrival_station':result_search['arrival_station'], 'departure_time':result_search['departure_time'], 'arrival_time':result_search['arrival_time'], 'type':"Express", 'price':result_search['price']}
        result = requests.post("http://localhost:8080/ticket", params=params_ticket)
        result_ticket = json.loads(result.text)
    
        result_ticket_string = "Your train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])

    send_mail(session.get('email', None), "California Ultra-Speed Rail Train Purchase", result_ticket_string+"\n Your total price in this transaction is: "+str(total_price+1))
    return render_template('confirmation.html', ticket_info = result_ticket_string)    

@app.route("/confirmation4", methods=['GET', 'POST'])
def confirmation4():
    
    result_search = session.get('result4', None)
    print session.get('email', None)

    total_price = session.get('total_price4', None)
    #localhost:8080/transaction?user_email=111&price=150
    #localhost:8080/ticket?transaction_id=4&user_name=222&train_name=SB1700 2017-12-25&departure_station=A&arrival_station=Z&departure_time=2000&arrival_time=2100&type=express&price=90
    
    params_transaction = {'user_email':session.get('email', None), 'price': total_price}
    result = requests.post("http://localhost:8080/transaction", params=params_transaction)
    result_transaction = json.loads(result.text)

    if str(result_search['transfer']) == "True":
        params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':result_search['train_name'], 'departure_station':result_search['departure_station'], 'arrival_station':result_search['arrival_station'], 'departure_time':result_search['departure_time'], 'arrival_time':result_search['arrival_time'], 'type':"Express", 'price':result_search['price']}
        result = requests.post("http://localhost:8080/ticket", params=params_ticket)
        result_ticket = json.loads(result.text)
    
        result_ticket_string = "Your train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
        length = len(result_search['transfer_train'])
        if length == 1:
            temp_train = result_search['transfer_train'][0]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
        else:
            temp_train = result_search['transfer_train'][0]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])

            temp_train = result_search['transfer_train'][1]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
    
    else:
        params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':result_search['train_name'], 'departure_station':result_search['departure_station'], 'arrival_station':result_search['arrival_station'], 'departure_time':result_search['departure_time'], 'arrival_time':result_search['arrival_time'], 'type':"Express", 'price':result_search['price']}
        result = requests.post("http://localhost:8080/ticket", params=params_ticket)
        result_ticket = json.loads(result.text)
    
        result_ticket_string = "Your train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])

    send_mail(session.get('email', None), "California Ultra-Speed Rail Train Purchase", result_ticket_string+"\n Your total price in this transaction is: "+str(total_price+1))
    return render_template('confirmation.html', ticket_info = result_ticket_string)    

@app.route("/confirmation5", methods=['GET', 'POST'])
def confirmation5():
    
    result_search = session.get('result5', None)
    print session.get('email', None)

    total_price = session.get('total_price5', None)
    #localhost:8080/transaction?user_email=111&price=150
    #localhost:8080/ticket?transaction_id=4&user_name=222&train_name=SB1700 2017-12-25&departure_station=A&arrival_station=Z&departure_time=2000&arrival_time=2100&type=express&price=90
    
    params_transaction = {'user_email':session.get('email', None), 'price': total_price}
    result = requests.post("http://localhost:8080/transaction", params=params_transaction)
    result_transaction = json.loads(result.text)

    if str(result_search['transfer']) == "True":
        params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':result_search['train_name'], 'departure_station':result_search['departure_station'], 'arrival_station':result_search['arrival_station'], 'departure_time':result_search['departure_time'], 'arrival_time':result_search['arrival_time'], 'type':"Express", 'price':result_search['price']}
        result = requests.post("http://localhost:8080/ticket", params=params_ticket)
        result_ticket = json.loads(result.text)
    
        result_ticket_string = "Your train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
        length = len(result_search['transfer_train'])
        if length == 1:
            temp_train = result_search['transfer_train'][0]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
        else:
            temp_train = result_search['transfer_train'][0]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])

            temp_train = result_search['transfer_train'][1]
            params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':temp_train['train_name'], 'departure_station':temp_train['departure_station'], 'arrival_station':temp_train['arrival_station'], 'departure_time':temp_train['departure_time'], 'arrival_time':temp_train['arrival_time'], 'type':"Express", 'price':temp_train['price']}
            result = requests.post("http://localhost:8080/ticket", params=params_ticket)
            result_ticket = json.loads(result.text)
    
            result_ticket_string += ", \n Next train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])
    
    else:
        params_ticket = {'transaction_id':result_transaction['id'], 'user_name':result_transaction['user']['name'], 'train_name':result_search['train_name'], 'departure_station':result_search['departure_station'], 'arrival_station':result_search['arrival_station'], 'departure_time':result_search['departure_time'], 'arrival_time':result_search['arrival_time'], 'type':"Express", 'price':result_search['price']}
        result = requests.post("http://localhost:8080/ticket", params=params_ticket)
        result_ticket = json.loads(result.text)
    
        result_ticket_string = "Your train name is: " + result_ticket['train']['name'] + " , departure_station is: " + result_ticket['departure_station'] + ", departure_time is: " + result_ticket['departure_time'] + ", arrival_station is: " + result_ticket['arrival_station'] + ", arrive time is: " + result_ticket['arrival_time'] + ", price is: " + str(result_ticket['price']) + ", purchase date is: " + str(result_ticket['date'])

    send_mail(session.get('email', None), "California Ultra-Speed Rail Train Purchase", result_ticket_string+"\n Your total price in this transaction is: "+str(total_price))
    return render_template('confirmation.html', ticket_info = result_ticket_string)    


def send_mail(email, subject, context):
    msg = mail.send_message(
        subject,
        sender='sjsu.cmpe273.test@gmail.com',
        recipients=[email],
        body=context
    )
    return email
