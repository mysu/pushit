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

        function connect(){
            var socket = new WebSocket('ws://' +  location.host + '/gate');
            stompClient = Stomp.over(socket);
            stompClient.connect({}, connectHandle, errorHandle);
        }

        function connectHandle(frame){
            console.log('Connected: ' + frame);
            stompClient.send('/app/msgbus/init', {}, 'init');
            stompClient.subscribe('/topic/msgbus', function (data){
                console.log(data.body);
                component.setState({data: JSON.parse(data.body)});
            });
        }

        function errorHandle(message){
            if(stompClient != null){
                stompClient.disconnect();
                stompClient = null;
            }

            console.log('Disconnected: ' +  message);
        }

        connect();
    },
    render: function(){
        var msgs = [];
        var pushitData = this.state.data;
        console.log(pushitData.length);
        for(var i = 0; i < pushitData.length; i++){
            msgs.push(<PushItem msg={pushitData[i].msg}/>);
        }
        return (
            <div className='jumbotron container-fluid'>
                <h2>Messages</h2>
                {msgs}
            </div>
        );
    }
});

var PushItem = React.createClass({
    render: function(){
        var msg = this.props.msg;
        return (
            <div className='jumbotron'>
                <a href='{msg}'>{msg}</a>
            </div>
        );
    }
});

ReactDOM.render(
    <PushIt />,
    document.getElementById('container')
);