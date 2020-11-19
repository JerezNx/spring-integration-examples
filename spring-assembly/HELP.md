# Getting Started

依赖拆分打包：
```shell script
mvn -U clean package -P separate assembly:single -Dmaven.test.skip=true
```

单独jar打包：
```shell script
mvn -U clean package -P single assembly:single -Dmaven.test.skip=true
```
