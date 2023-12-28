const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  lintOnSave:false,
  devServer:{
    client:{
      overlay:false
    }
  }
  // devServer:{
  //   proxy:'http://localhost:8088',
    
  //   //  proxy:{
  //   //   '/api':{
  //   //      target:'http://localhost:8088/',
  //   //      ws:true,
  //   //      changOrigin:true,
  //   //      pathRewrite: {
  //   //       "^/api": "",
  //   //     },
  //   //   }
  //   //  }
  // }
})
