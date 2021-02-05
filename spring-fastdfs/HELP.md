# Getting Started

### 1.搭建fastdfs
1.
season/fastdfs
```
docker run -ti -d --name trakcer -v ~/tracker_data:/fastdfs/tracker/data --net=host season/fastdfs tracker
```

```shell script
42.192.183.108
docker run -tid --name storage -v ~/storage_data:/fastdfs/storage/data -v ~/store_path:/fastdfs/store_path --net=host -e TRACKER_SERVER:42.192.183.108:22122 -e GROUP_NAME=group1 season/fastdfs storage
```
2.集成nginx
delron/fastdfs
```shell script
docker run -dti --network=host --name tracker -v /var/fdfs/tracker:/var/fdfs -v /etc/localtime:/etc/localtime delron/fastdfs tracker
```

```shell script
docker run -dti  --network=host --name storage -e TRACKER_SERVER=42.192.183.108:22122 -v /var/fdfs/storage:/var/fdfs  -v /etc/localtime:/etc/localtime  delron/fastdfs storage
```

42.192.183.108:8888/group1/M00/00/00/rBEAAl_IqcmAaot6AAEyg5eAz14093.jpg
