var Pushit = React.createClass({
    getInitialState: function(){
        return {data: []};
    },
    componentDidMount: function(){
        this.connectToServer();
    },
    connectToServer: function(){
        var stomClient = null;
        var component = this;

        function connect(){
            var socket = new WebSocket('ws://' +  location.host + '/gate');
            stomClient = Stom.over(socket);
            stomClient.connect({}, connectHandle, errorHandle);
        }

        function connectHandle(frame){
            console.log('Connected: ' + frame);
            stomClient.send('/app/msgbus/init', {}, 'init');
            stomClient.subscribe('/topic/msgbus', function (data){
                console.log(data.body);
                component.setState({data: JSON.parse(data.body)});
            });
        }

        function errorHandle(message){
            if(stomClient != null){
                stomClient.disconnect();
                stomClient = null;
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