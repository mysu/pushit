var PushIt = React.createClass({
    getInitialState: function(){
        return {data: []};
    },
    componentDidMount: function(){
        this.connectToServer();
    },
    connectToServer: function(){
        var stompClient = null;
        var component = this;
        var pushId;
        var pushServer;

        function getPushId(){
            fetch('http://' + location.host + "/push")
                .then((response) =>{
                    console.log(response);
                    return response.json();
                }).then((val)=>{
                    if(val && val.success){
                        pushId=val.pushId;
                        pushServer = val.server;
                        jQuery('#qrcode').qrcode({text: pushServer + "--" + pushId});
                        connect();
                    }else{
                        console.log("Error: " +  val);
                     }
                });
        }

        function connect(pushId){
            var socket = new WebSocket('ws://' +  location.host + '/gate');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, connectHandle, errorHandle);
        }

        function connectHandle(frame){
            console.log('Connected: ' + frame);
            stompClient.send('/app/msgbus/init', {"pushId": pushId}, 'init');
            stompClient.subscribe('/topic/msgbus/' + pushId, function (data){
                console.log(data.body);
                var items = JSON.parse(data.body).concat(component.state.data);
                component.setState({pushId: pushId, data: items});
            });
        }

        function errorHandle(message){
            if(stompClient != null){
                stompClient.disconnect();
                stompClient = null;
            }

            console.log('Disconnected: ' +  message);
        }

        getPushId();
    },
    render: function(){
        var msgs = [];
        var pushitData = this.state.data;
        console.log(pushitData.length);
        for(var i = 0; i < pushitData.length; i++){
            msgs.push(<PushItem msg={pushitData[i].msg}/>);
        }
        return (
        <div>
           <div className='panel panel-default'>
             <div className='panel-heading'>Messages</div>
             <div className='panel-body'>
               <div className='list-group'>
               {msgs}
               </div>
             </div>
           </div>
         </div>
        );
    }
});

var PushItem = React.createClass({
    render: function(){
        var msg = this.props.msg;
        return (
            <a href={msg} target="_blank" className='list-group-item'>{msg}</a>
        );
    }
});

ReactDOM.render(
    <PushIt />,
    document.getElementById('messages')
);
