const express = require("express");
const bodyParser = require("body-parser");
const ejs = require("ejs");
const qrcode = require("qrcode");
const firebase = require("firebase");
const math  = require("mathjs");
const app = express();



// const firebaseConfig = {
//   apiKey: "AIzaSyD9zD45t1ZpHSsoUHycHHc0mzqnXw3x4qg",
//   authDomain: "projectq-aec73.firebaseapp.com",
// databaseURL:"https://projectq-aec73-default-rtdb.firebaseio.com/",
//   projectId: "projectq-aec73",
//   storageBucket: "projectq-aec73.appspot.com",
//   messagingSenderId: "328212184716",
//   appId: "1:328212184716:web:056c24de4e4cdbc93605a9",
//   measurementId: "G-529Z6MPKP8"
// };
// const firebaseConfig = {
//   apiKey: "AIzaSyAlX99SAawBPK5_tUVJYapBKt2c-s8tpwI",
//   authDomain: "at-qr-d4a78.firebaseapp.com",
//   databaseURL: "https://at-qr-d4a78-default-rtdb.firebaseio.com/",
//   projectId: "at-qr-d4a78",
//   storageBucket: "at-qr-d4a78.appspot.com",
//   messagingSenderId: "102565788043",
//   appId: "1:102565788043:web:20babdc849802e845b978e",
//   measurementId: "G-D86BVBSV2V"
// };
const firebaseConfig = {
  apiKey: "AIzaSyDUtsZYuEGaYSI8xfNFrSI8lvHWmXb4YeE",
  authDomain: "scandie.firebaseapp.com",
  databaseURL: "https://scandie-default-rtdb.firebaseio.com",
  projectId: "scandie",
  storageBucket: "scandie.appspot.com",
  messagingSenderId: "830797174999",
  appId: "1:830797174999:web:99bf36c7622fb8405c7892",
  measurementId: "G-9GDMEE0WWJ"
};

// Initialize Firebase
firebase.initializeApp(firebaseConfig);
let db = firebase.database()


app.set('view engine', 'ejs');
app.use(bodyParser.urlencoded({extended: true}));
app.use(express.static( __dirname +"/public"));

app.listen(3000, function() {
  console.log("Server started on port 3000");
});

//Global Variable
var dt = "";
var subName = "";
var qr = ""
let rollG= 0 ;
let nameR = "";
var nameRe = [];
// var tcs_count=0;
// var dwm_count=0;
// var ip_count=0;
// var se_count =0;
// var pce_count=0;
// var cn_count=0;
// var tcs_total=0;
// var  dwm_total=0;
// var  ip_total=0;
// var se_total=0;
// var pce_total=0;
// var cn_total =0;

app.get("/",function(req,res){
  res.render("login",{

  });
});

app.get("/dashboard",function(req,res){
  res.render("dashboard",{

  });
});
app.post("/",function(req,res){
  var name = req.body.Aname;
  var pass = req.body.Apass;
  if(name== "Admin")
  {
    if(pass == "1234")
    {
      res.redirect("/home");
    }
    else{
      res.redirect("/")
    }
  }
});

app.get("/home",function(req,res){
  var sub = db.ref("/Subject/");
  sub.once('value',(snapshot)=>{
    let data = Object.keys(JSON.parse(JSON.stringify(snapshot.val())));
    qr = subName+"-"+dt;
    if(qr!=""){
    qrcode.toDataURL(qr,(err,src)=>{
      if (err) res.send("Something went wrong!!");
        res.render("home", {
          qr_code: src,
          sub:data

        });
    })
  }
  else{
    console.log("else part");
    res.render("home", {
      qr_code: "",
      sub :data

    });
  }

});

});
app.post("/home",function(req,res){
  input_text = req.body.text11;
 dt = req.body.dt;
 subName = req.body.SubName;
  console.log(subName);
  console.log(dt);
  let Pnode = db.ref("/Subject/"+subName+"/"+dt);
  Pnode.set("");
  res.redirect("/home");
});

app.get("/present",function(req,res){
  let roll = ["0"];
  let nameS = [];
  let Pnode = db.ref("/Subject/"+subName+"/"+dt);
  Pnode.once('value', (snapshot) => {
  let data = JSON.parse(JSON.stringify(snapshot.val()));
  let count = data.length-1;
  console.log(count);
  var keys = Object.keys(data);
  for(let i=0;i<=keys.length-1;i++)
  {
    roll.push(keys[i]);
  }
  console.log(roll);
  var na =[""];
  ///
  for(var i = 1 ; i <roll.length+1;i++)
   {
     let nNode = db.ref("/Subject/"+subName+"/"+dt+"/"+roll[i]+"/name/");
     nNode.on('value',(snapshot)=>{
       let ndata = JSON.parse(JSON.stringify(snapshot.val()));
       console.log("mm"+ndata);
       na.push(ndata);
     });
     console.log(na);
     if(i == roll.length)
     {
       console.log("inside");
       res.render("present",{
          roll:roll,
          name :na,
        })
     }
     if(i==roll.length)
     {
       break;
     }
     console.log("//"+na);

   }

  ///


  // res.render("present",{
  //   roll:roll,
  //   name :nameS,
  // })

});
});

app.get("/scrap",function(req,res){
  res.render("scrap",{

  });
});
app.get("/report",function(req,res){

  let roll = [];
  let nameS = [];
  let Pnode = db.ref("/Student/");
  Pnode.once('value', (snapshot) => {
    var n = [];
  let data = JSON.parse(JSON.stringify(snapshot.val()));
  let count = data.length-1;
  console.log("count:"+count);
  var keys = Object.keys(data);
  for(let i=0;i<keys.length;i++)
  {
    roll.push(keys[i]);
  }
let na = [];
 for(var i = 0 ; i <roll.length+1;i++)
  {
    let nNode = db.ref("/Student/"+roll[i]+"/name/");
    nNode.on('value',(snapshot)=>{

      let ndata = JSON.parse(JSON.stringify(snapshot.val()));
      na.push(ndata);
      console.log("i"+i);
    });
    if(i == roll.length)
    {
      console.log("inside");
      res.render("report",{
         roll:roll,
         name :na,
       })
    }
    if(i==roll.length)
    {
      break;
    }
    console.log("//"+na);

  }
});
});
app.post("/report",function(req,res){
  let roll = req.body.rollNum;
  let name = req.body.name;
  rollG = roll;
  nameR = name;

  let name_node = db.ref("Student/"+rollG+"/");
  name_node.once("value",(snapshot)=>{
    let data = JSON.parse(JSON.stringify(snapshot.val()));
    console.log(data.name);
  });
  res.redirect("/Honeycomb");
});
app.get("/sidebar",function(req,res){
  res.render("sidebar",{
  });
});
app.get("/Honeycomb",function(req,res){
  var tcs_count=0;
  var dwm_count=0;
  var ip_count=0;
  var se_count =0;
  var pce_count=0;
  var cn_count=0;
  var tcs_total=0;
  var  dwm_total=0;
  var  ip_total=0;
  var se_total=0;
  var pce_total=0;
  var cn_total =0;

  async function run(){
    //PerSub
    let tcs_count_node = db.ref("/Student/"+rollG+"/Subject/TCS");
    await tcs_count_node.once('value',(snapshot)=>{

      console.log("TCS count");
      let data = JSON.parse(JSON.stringify(snapshot.val()));
      if(data!=null){
      var len = Object.keys(data).length;
      tcs_count = len;
    }
    else{
      tcs_count = 0;
    }


    });
    console.log("///"+tcs_count);

    let dwm_count_node = db.ref("/Student/"+rollG+"/Subject/DWM");
    await dwm_count_node.once('value',(snapshot)=>{
      console.log("dwm c");

      let data = JSON.parse(JSON.stringify(snapshot.val()));
      if(data!=null){
      var len = Object.keys(data).length;
      dwm_count = len;
    }
    else{
      dwm_count = 0;
    }
      });
    let cn_count_node = db.ref("/Student/"+rollG+"/Subject/CN");
    await cn_count_node.once('value',(snapshot)=>{
      console.log("cn count");
      let data = JSON.parse(JSON.stringify(snapshot.val()));
      if(data!=null){
      var len = Object.keys(data).length;
      cn_count = len;
    }
    else{
      cn_count = 0;
    }
    console.log(cn_count);
    });
    let ip_count_node = db.ref("/Student/"+rollG+"/Subject/IP");
    await ip_count_node.once('value',(snapshot)=>{
      console.log("ip c");
      let data = JSON.parse(JSON.stringify(snapshot.val()));
      if(data!=null){
      var len = Object.keys(data).length;
      ip_count= len;
    }
    else{
      ip_count = 0;
    }
    });

    let se_count_node = db.ref("/Student/"+rollG+"/Subject/SE");
    await se_count_node.once('value',(snapshot)=>{
      console.log("se c");
      let data = JSON.parse(JSON.stringify(snapshot.val()));
      if(data!=null){
      var len = Object.keys(data).length;
      se_count = len;
    }
    else{
      se_count = 0;
    }
    });

  //total
  let tcs_total_node = db.ref("/Subject/TCS");
  await tcs_total_node.on('value',(snapshot)=>{
    console.log("TCS");
    let data = JSON.parse(JSON.stringify(snapshot.val()));
    if(data!=null){
    var len = Object.keys(data).length;
    tcs_total = len;
  }

    console.log("tcs total"+len);
  });

  let dwm_total_node = db.ref("/Subject/DWM");
  await dwm_total_node.once('value',(snapshot)=>{
    console.log("dwm");
    let data = JSON.parse(JSON.stringify(snapshot.val()));
    if(data!=null){
    var len = Object.keys(data).length;
    dwm_total = len
  };
  });
  cn_total_node = db.ref("/Subject/CN");

  await cn_total_node.once('value',(snapshot)=>{
    console.log("cn");
    let data = JSON.parse(JSON.stringify(snapshot.val()));
    if(data!=null){
    var len = Object.keys(data).length;
    cn_total = len;
  }
  console.log(cn_total);
  });
  ip_total_node = db.ref("/Subject/IP");
  await ip_total_node.once('value',(snapshot)=>{
    console.log("ip");
    let data = JSON.parse(JSON.stringify(snapshot.val()));
    if(data!=null){
    var len = Object.keys(data).length;
    ip_total = len;
  }
  });

  se_total_node = db.ref("/Subject/SE");
  await se_total_node.once('value',(snapshot)=>{
    console.log("se");
    let data = JSON.parse(JSON.stringify(snapshot.val()));
    if(data!=null){
    var len = Object.keys(data).length;
    se_total = len;
  }
  });
  var tcs_per = math.round((tcs_count/tcs_total)*100);
  var se_per = math.round((se_count/se_total)*100);
  var dwm_per = math.round((dwm_count/dwm_total)*100);
  var ip_per = math.round((ip_count/ip_total)*100);
  var cn_per = math.round((cn_count/cn_total)*100);


  update_node = db.ref("/rollno/rollno/"+rollG+"/Subject/");
  update_node.update({
    "CN":cn_per,
    "DWM":dwm_per,
    "IP":ip_per,
    "SE":se_per,
    "TCS":tcs_per
  });

  await res.render("Honeycomb",{

    name:nameR,
    roll: rollG,

    tcs_per : tcs_per,
    dwm_per : dwm_per,
    ip_per : ip_per,
    se_per :se_per,
    cn_per :cn_per,
  });
}

run();
});





//
// 1.hidden date time input
// 2.Dropdown list from databse of subject
// 3.fetching student data
